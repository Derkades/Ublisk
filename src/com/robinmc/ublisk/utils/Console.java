package com.robinmc.ublisk.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.robinmc.ublisk.utils.logging.LogLevel;
import com.robinmc.ublisk.utils.logging.Logger;

import net.md_5.bungee.api.ChatColor;

public class Console {
	
	/**
	 * Execute a command as the console (without the /)
	 * @param cmd The command to be executed
	 */
	public static void sendCommand(String cmd){
		try {
			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), cmd);
		} catch (Exception e){
			sendMessage("An error occured while attempting to perform a console command!");
			sendMessage("Command: " + cmd);
		}
	}

}