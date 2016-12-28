package com.robinmc.ublisk.utils.java;

import java.util.List;

public class ListUtils {
	
	public static boolean stringListContainsString(List<String> list, String string){
		boolean contains = false;
		for (String entry : list){
			if (entry.contains(string)){
				contains = true;
			}
		}
		return contains;
	}

}
