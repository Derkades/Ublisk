package com.robinmc.ublisk.utils;

public class StringUtils {
	
	public static String getStringFromWords(String[] words){
		String msg = "";
		
		for (String word : words){
			msg = String.join(msg, word + " ");
		}
		
		return msg;
	}

}
