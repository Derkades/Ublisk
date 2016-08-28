package com.robinmc.ublisk.utils.logging;

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
		ChatColor color = logLevel.getColor();
		String msg = color + string;
		
		System.out.println(msg);
		
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
