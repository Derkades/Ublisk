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
	public void log(Object object){		
		switch (logLevel){
		case WARNING:
			Bukkit.getLogger().log(Level.WARNING, object + "");
			break;
		case SEVERE:
			Bukkit.getLogger().log(Level.WARNING, object + "");
			break;
		default:
			if (Config.getBoolean("log")){
				Bukkit.getLogger().log(Level.INFO, object + "");
			}
			break;
		}
	}
	
	public static void log(LogLevel logLevel, Object object){
		Logger.getLogger(logLevel).log(object);
	}
	
	public static void log(LogLevel logLevel, String name, Object object){
		log(logLevel, "[" + name + "] " + object);
	}
	
	public static Logger getLogger(LogLevel logLevel){
		return new Logger(logLevel);
	}
	
}
