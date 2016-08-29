package com.robinmc.ublisk.utils.logging;

public enum LogLevel {
	
	/**
	 * To be used for messages that will/should be removed later.
	 * Color: White
	 */
	DEBUG("Debug"),
	
	/**
	 * To be used for messages providing not very important information
	 * Color: Green
	 */
	INFO("Info"),
	
	/**
	 * To be used for messages providing more important information, such as unexpected player movement
	 * Color: Yellow
	 */
	WARNING("Warning"),
	
	/**
	 * To be used to alert the owner of very important information, such as a critical error in the code or an unexpected reading from a file.
	 * Color: Red
	 */
	SEVERE("Severe");
	
	private String string;
	
	LogLevel(String string){
		this.string = string;
	}
	
	public String getString(){
		return string;
	}

}
