package com.robinmc.ublisk.utils.logging;

import java.util.logging.Level;

import org.bukkit.Bukkit;

import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.Ublisk;

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
	
}
