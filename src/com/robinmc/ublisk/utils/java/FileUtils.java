package com.robinmc.ublisk.utils.java;

import java.io.File;
import java.io.IOException;

public class FileUtils {
	
	public static String getFileName(File file){
		String name = file.getName();
		int pos = name.lastIndexOf(".");
		if (pos > 0) {
		    name = name.substring(0, pos);
		}
		return name;
	}
	
	public static void appendStringToFile(File file, String string){
		try {
			org.apache.commons.io.FileUtils.writeStringToFile(file, string, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
