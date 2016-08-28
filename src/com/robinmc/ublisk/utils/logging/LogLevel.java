package com.robinmc.ublisk.utils.logging;

import net.md_5.bungee.api.ChatColor;

public enum LogLevel {
	
	/**
	 * To be used for messages that will/should be removed later.
	 * Color: White
	 */
	DEBUG("Debug", ChatColor.WHITE),
	
	/**
	 * To be used for messages providing not very important information
	 * Color: Green
	 */
	INFO("Info", ChatColor.GREEN),
	
	/**
	 * To be used for messages providing more important information, such as unexpected player movement
	 * Color: Yellow
	 */
	WARNING("Warning", ChatColor.YELLOW),
	
	/**
	 * To be used to alert the owner of very important information, such as a critical error in the code or an unexpected reading from a file.
	 * Color: Red
	 */
	SEVERE("Severe", ChatColor.RED);
	
	private String string;
	private ChatColor color;
	
	LogLevel(String string, ChatColor color){
		this.string = string;
	}
	
	public String getString(){
		return string;
	}
	
	public ChatColor getColor(){
		return color;
	}

}
