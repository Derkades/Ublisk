package com.robinmc.ublisk.utils;

import java.util.logging.Level;

import org.bukkit.Bukkit;

import net.md_5.bungee.api.ChatColor;

public class Logger {

	public static void log(LogLevel logLevel, String name, Object object){
		String consoleMessage = "[" + name + "] " + object;
		String chatMessage = ChatColor.GRAY + "[" + logLevel + "] " + object;
		if (logLevel == LogLevel.SEVERE || logLevel == LogLevel.WARNING)
			Bukkit.getLogger().log(Level.WARNING, consoleMessage);
		else
			Bukkit.getLogger().log(Level.INFO, consoleMessage);
		
		for (UPlayer player : Ublisk.getOnlinePlayers()){
			player.sendMessage(chatMessage);
		}
	}
	
	public static void log(LogLevel logLevel, Object object){
		log(logLevel, "Ublisk", object);
	}
	
	public static enum LogLevel {
		
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
	
}
