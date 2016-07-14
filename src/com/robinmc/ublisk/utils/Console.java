package com.robinmc.ublisk.utils;

import org.bukkit.Bukkit;

public class Console {
	
	/**
	 * Sends a message to the console
	 * @param msg Message to be sent
	 */
	public static void sendMessage(String msg){
		/*
		try {
			Bukkit.getServer().getConsoleSender().sendMessage(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		System.out.println(msg);
	}
	
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