/*---------------------------------------------------------------------------*\
  $Id$
  ---------------------------------------------------------------------------
  This software is released under a BSD-style license:

  Copyright (c) 2004-2007 Brian M. Clapper. All rights reserved.

  Redistribution and use in source and binary forms, with or without
  modification, are permitted provided that the following conditions are
  met:

  1.  Redistributions of source code must retain the above copyright notice,
      this list of conditions and the following disclaimer.

  2.  The end-user documentation included with the redistribution, if any,
      must include the following acknowlegement:

        "This product includes software developed by Brian M. Clapper
        (bmc@clapper.org, http://www.clapper.org/bmc/). That software is
        copyright (c) 2004-2007 Brian M. Clapper."

      Alternately, this acknowlegement may appear in the software itself,
      if wherever such third-party acknowlegements normally appear.

  3.  Neither the names "clapper.org", "clapper.org Java Utility Library",
      nor any of the names of the project contributors may be used to
      endorse or promote products derived from this software without prior
      written permission. For written permission, please contact
      bmc@clapper.org.

  4.  Products derived from this software may not be called "clapper.org
      Java Utility Library", nor may "clapper.org" appear in their names
      without prior written permission of Brian M. Clapper.

  THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
  MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN
  NO EVENT SHALL BRIAN M. CLAPPER BE LIABLE FOR ANY DIRECT, INDIRECT,
  INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
  NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
  DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
  THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
  THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
\*---------------------------------------------------------------------------*/

package org.clapper.util.classutil;

import java.io.File;

import java.net.URL;
import java.net.URLClassLoader;
import java.net.MalformedURLException;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.StringTokenizer;

import org.clapper.util.logging.Logger;

/**
 * A <tt>ClassLoaderBuilder</tt> is used to build an alternate class loader
 * that includes additional jar files, zip files and/or directories in its
 * load path. It's basically a convenient wrapper around
 * <tt>java.net.URLClassLoader</tt>.
 *
 * @version <tt>$Revision$</tt>
 *
 * @author Copyright &copy; 2006 Brian M. Clapper
 */
public class ClassLoaderBuilder
{
    /*----------------------------------------------------------------------*\
                            Private Data Items
    \*----------------------------------------------------------------------*/

    private Collection<URL> urlList = new LinkedHashSet<URL>();

    /**
     * For logging
     */
    private static final Logger log = new Logger (ClassLoaderBuilder.class);

    /*----------------------------------------------------------------------*\
                                Constructor
    \*----------------------------------------------------------------------*/

    /**
     * Create a new <tt>ClassLoaderBuilder</tt>.
     */
    public ClassLoaderBuilder()
    {
        // Nothing to do
    }

    /*----------------------------------------------------------------------*\
                              Public Methods
    \*----------------------------------------------------------------------*/

    /**
     * Add a jar file, zip file or directory to the list of places the
     * not-yet-constructed class loader will search. If the directory or
     * file does not exist, or isn't a jar file, zip file, or directory,
     * this method just ignores it and returns <tt>false</tt>.
     *
     * @param file  the jar file, zip file or directory
     *
     * @return <tt>true</tt> if the file was suitable for adding;
     *         <tt>false</tt> if it was not a jar file, zip file, or
     *         directory.
     */
    public boolean add (File file)
    {
        boolean added    = false;
        String  fileName = file.getPath();

        try
        {
            if (ClassUtil.fileCanContainClasses (file))
            {
                if (file.isDirectory() && (! fileName.endsWith ("/")))
                {
                    fileName = fileName + "/";
                    file = new File(fileName);
                }

                urlList.add(file.toURI().toURL());
                added = true;
            }
        }

        catch (MalformedURLException ex)
        {
            log.error ("Unexpected exception", ex);
        }

        if (! added)
        {
            log.debug ("Skipping non-jar, non-zip, non-directory \"" +
                       fileName +
                       "\"");
        }

        return added;
    }

    /**
     * Add an array of jar files, zip files or directories to the list of
     * places the not-yet-constructed class loader will search. If the
     * directory or file does not exist, or isn't a jar file, zip file, or
     * directory, this method just ignores it and returns <tt>false</tt>.
     *
     * @param files  the array
     *
     * @return the number of entries from the array that were actually added
     */
    public int add (File[] files)
    {
        int total = 0;

        for (File f : files)
        {
            if (add (f))
                total++;
        }

        return total;
    }


    /**
     * Add a <tt>Collection</tt> of jar files, zip files or directories to
     * the list of places the not-yet-constructed class loader will search.
     * If the directory or file does not exist, or isn't a jar file, zip
     * file, or directory, this method just ignores it and returns
     * <tt>false</tt>.
     *
     * @param files  the collection
     *
     * @return the number of entries from the collection that were
     *         actually added
     */
    public int add (Collection<File> files)
    {
        int total = 0;

        for (File f : files)
        {
            if (add (f))
                total++;
        }

        return total;
    }

    /**
     * Add the contents of the classpath.
     */
    public void addClassPath()
    {
        String path = null;

        try
        {
            path = System.getProperty ("java.class.path");
        }

        catch (Exception ex)
        {
            path= "";
            log.error ("Unable to get class path", ex);
        }
    
        if (path != null)
        {
            StringTokenizer tok = new StringTokenizer (path,
                                                       File.pathSeparator);

            while (tok.hasMoreTokens())
                add (new File (tok.nextToken()));
        }
    }

    /**
     * Clear the stored files in this object.
     */
    public void clear()
    {
        urlList.clear();
    }

    /**
     * Create and return a class loader that will search the additional
     * places defined in this builder. The resulting class loader uses
     * the default delegation parent <tt>ClassLoader</tt>.
     *
     * @throws SecurityException if a security manager exists and its
     *                           <tt>checkCreateClassLoader()</tt> method
     *                           does not allow creation of a class loader
     */
    public ClassLoader createClassLoader()
        throws SecurityException
    {
        return new URLClassLoader (urlList.toArray (new URL[urlList.size()]),
                                   getClass().getClassLoader());
    }

    /**
     * Create and return a class loader that will search the additional
     * places defined in this builder. The resulting class loader uses
     * the specified parent <tt>ClassLoader</tt>.
     *
     * @param parentLoader the desired parent class loader
     *
     * @throws SecurityException if a security manager exists and its
     *                           <tt>checkCreateClassLoader()</tt> method
     *                           does not allow creation of a class loader
     */
    public ClassLoader createClassLoader (ClassLoader parentLoader)
        throws SecurityException
    {
        return new URLClassLoader (urlList.toArray (new URL[urlList.size()]),
                                                    parentLoader);
    }
}
