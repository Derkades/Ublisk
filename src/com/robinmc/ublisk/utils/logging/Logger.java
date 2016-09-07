package com.robinmc.ublisk.utils.logging;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

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
		ChatColor color;
		
		switch (logLevel){
		case DEBUG: color = ChatColor.WHITE;
		case INFO: color = ChatColor.GREEN;
		case WARNING: color = ChatColor.YELLOW;
		case SEVERE: color = ChatColor.RED;
		default: color = ChatColor.GOLD;
		}
		
		switch (logLevel){
		case WARNING:
			Bukkit.getLogger().log(Level.WARNING, string);
		case SEVERE:
			Bukkit.getLogger().log(Level.WARNING, string);
		default:
			Bukkit.getLogger().log(Level.INFO, string);
		}

		String msg = color + string;
		
		for (Player player : Bukkit.getOnlinePlayers()){
			if (player.isOp()){
				player.sendMessage(msg);
			}
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
