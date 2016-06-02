package com.robinmc.ublisk.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.robinmc.ublisk.Messages;

public class Help implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 0){
			sender.sendMessage(ChatColor.GOLD + "Ublisk help menu - Please choose a catagory");
			sender.sendMessage("");
			sender.sendMessage(ChatColor.GOLD + "1" + ChatColor.GRAY + ")" + ChatColor.YELLOW + " FAQ");
			sender.sendMessage(ChatColor.GOLD + "2" + ChatColor.GRAY + ")" + ChatColor.YELLOW + " Commands");
			sender.sendMessage("");
			sender.sendMessage(ChatColor.GRAY + "Type /help [number] for help regarding that catagory");
			return true;
		} else {
			sender.sendMessage(Messages.wrongUsage());
			return true;
		}
	}

}
