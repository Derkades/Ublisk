package com.robinmc.ublisk.utils.java;

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

}
