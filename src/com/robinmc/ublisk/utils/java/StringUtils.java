package com.robinmc.ublisk.utils.java;

public class StringUtils {

	/**
	 * @param string
	 * @param spaces False if spaces should be considered as non-alphanumeric characters. (e.g. "hello world" will return false but "helloworld" will return true)
	 * @return True if the specified string contains characters other than A-Z, a-z, 0-9 and optionally spaces.
	 */
	public static boolean containsNonAlphanumericalCharacters(String string, boolean allowSpaces){
		String withoutSpecialCharacters;
		
		if (allowSpaces){
			withoutSpecialCharacters = string.replaceAll("[^A-Za-z0-9 ]", "");
		} else {
			withoutSpecialCharacters = string.replaceAll("[^A-Za-z0-9]", "");
		}

		//If the string is the same as the original it did not contain any non-alphanumeric characters
		return withoutSpecialCharacters.equals(string);
	}
	
	/**
	 * @param string
	 * @return true if the string contains only numbers, letters and underscores.
	 */
	public static boolean validateString(String string){
		for (char c : string.toCharArray()){
			if (!Character.isLetterOrDigit(c) & c != '_'){
				return false;
			}
		}
		return true;
	}

}
