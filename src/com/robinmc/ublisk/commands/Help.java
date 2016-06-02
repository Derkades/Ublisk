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
		} else if (args.length == 1){
			if (args[0].equals("1") || args[0].equalsIgnoreCase("faq")){
				sender.sendMessage("Ublisk faq menu - Please choose a catagory");
				sender.sendMessage("");
				sender.sendMessage(ChatColor.GOLD + "1" + ChatColor.GRAY + ")" + ChatColor.YELLOW + " General");
				sender.sendMessage(ChatColor.GOLD + "2" + ChatColor.GRAY + ")" + ChatColor.YELLOW + " Plugins");
				sender.sendMessage("");
				sender.sendMessage(ChatColor.GRAY + "Now type /help faq [number]");
				return true;
			} else if (args[0].equals("2") || args[0].equalsIgnoreCase("commands")){
				sender.sendMessage("Nothing here yet!");
				return true;
			} else {
				sender.sendMessage(Messages.wrongUsage());
				return true;
			}
		} else if (args.length == 2){
			if (args[0].equalsIgnoreCase("faq")){
				if (args[1].equals("1")){
					sender.sendMessage("Nothing here yet!");
					return true;
				} else if (args[1].equals("2")){
					sender.sendMessage(ChatColor.GOLD + "Q: Does this server use custom plugins?");
					sender.sendMessage(ChatColor.YELLOW + "A: Yes. Everything is custom coded except for API plugins. To see a full list of all plugins our server uses type /plugins");
					return true;
				} else {
					sender.sendMessage(Messages.wrongUsage());
					return true;
				}
			} else {
				sender.sendMessage(Messages.wrongUsage());
				return true;
			}
		} else {
			sender.sendMessage(Messages.wrongUsage());
			return true;
		}
	}

}
