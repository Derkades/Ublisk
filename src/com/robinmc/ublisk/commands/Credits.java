package com.robinmc.ublisk.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.robinmc.ublisk.Helper;

import static org.bukkit.ChatColor.*;

public class Credits implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		sender.sendMessage("");
		sender.sendMessage(DARK_AQUA + "Special thanks to:");
		for (Helper helper : Helper.values()){
			sender.sendMessage("");
			sender.sendMessage(DARK_AQUA + "" + BOLD + helper.getIgn());
			sender.sendMessage(AQUA + helper.getFunction());
			sender.sendMessage(GRAY + helper.getLink());
		}
		
		for (int i = 0; i <= 3 ; i++) sender.sendMessage("");
		
		sender.sendMessage(DARK_AQUA + "" + BOLD + " ^^^ " + AQUA + "Open chat and scroll up" + DARK_AQUA + "" + BOLD + " ^^^");
		
		return true;
	}

}
