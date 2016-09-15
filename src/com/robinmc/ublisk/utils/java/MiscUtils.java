package com.robinmc.ublisk.utils.java;

import java.io.File;

public class MiscUtils {
	
	/**
	 * Inverts a boolean (true -> false, false -> true)
	 * @param bool 
	 * @return Inverted boolean
	 */
	public static boolean invertBoolean(boolean bool){
		boolean inverted;
		
		if (bool){
			inverted = false;
		} else {
			inverted = true;
		}
		
		return inverted;
	}
	
	public static String getFileName(File file){
		String name = file.getName();
		int pos = name.lastIndexOf(".");
		if (pos > 0) {
		    name = name.substring(0, pos);
		}
		return name;
	}

}
