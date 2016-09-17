package com.robinmc.ublisk.utils.logging;

import java.util.logging.Level;

import org.bukkit.Bukkit;

import com.robinmc.ublisk.utils.Config;

public class Logger {
	
	private LogLevel logLevel;
	
	public Logger(LogLevel logLevel){
		this.logLevel = logLevel;
	}
	
	public LogLevel getLogLevel(){
		return logLevel;
	}

	/**
	 * Sends a message to the console
	 * @param string A message
	 */
	public void log(String string){		
		switch (logLevel){
		case WARNING:
			Bukkit.getLogger().log(Level.WARNING, string);
			break;
		case SEVERE:
			Bukkit.getLogger().log(Level.WARNING, string);
			break;
		default:
			if (Config.getBoolean("log")){
				Bukkit.getLogger().log(Level.INFO, string);
			}
			break;
		}
	}
	
	public static void log(LogLevel logLevel, String string){
		Logger.getLogger(logLevel).log(string);
	}
	
	public static void log(LogLevel logLevel, String name, String string){
		log(logLevel, "[" + name + "] " + string);
	}
	
	public static Logger getLogger(LogLevel logLevel){
		return new Logger(logLevel);
	}
	
}
