package com.robinmc.ublisk.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.robinmc.ublisk.Messages;

public class Help implements CommandExecutor {
	
	ChatColor gold = ChatColor.GOLD;
	ChatColor gray = ChatColor.GRAY;
	ChatColor yellow = ChatColor.YELLOW;
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (args.length == 0){
			sender.sendMessage("");
			sender.sendMessage(gold + "Ublisk help menu - Please choose a catagory");
			sender.sendMessage("");
			sender.sendMessage(gold + "1" + gray + ")" + yellow + " FAQ");
			sender.sendMessage(gold + "2" + gray + ")" + yellow + " Commands");
			sender.sendMessage("");
			sender.sendMessage(gray + "Type /help [number] for help regarding that catagory");
			return true;
		} else if (args.length == 1){
			if (args[0].equals("1") || args[0].equalsIgnoreCase("faq")){
				sender.sendMessage("");
				sender.sendMessage(gold + "Ublisk faq menu - Please choose a catagory");
				sender.sendMessage("");
				sender.sendMessage(gold + "1" + gray + ")" + yellow + " General");
				sender.sendMessage(gold + "2" + gray + ")" + yellow + " Plugins");
				sender.sendMessage("");
				sender.sendMessage(gray + "Now type /help faq [number]");
				return true;
			} else if (args[0].equals("2") || args[0].equalsIgnoreCase("commands")){
				sender.sendMessage("");
				sender.sendMessage(gold + "List of all commands - type /help command [command name or number]");
				sender.sendMessage("");
				sender.sendMessage(gold + "1" + gray + ")" + yellow + " Credits");
				sender.sendMessage(gold + "2" + gray + ")" + yellow + " Menu");
				sender.sendMessage(gold + "3" + gray + ")" + yellow + " Music");
				return true;
			} else {
				sender.sendMessage(Messages.wrongUsage());
				return true;
			}
		} else if (args.length == 2){
			if (args[0].equalsIgnoreCase("faq")){
				if (args[1].equals("1")){
					sender.sendMessage("");
					sender.sendMessage(gold + "Q: How can I get wood?");
					sender.sendMessage(yellow + "A: You can get wood by clicking the lever at the right side of the saw at 80 -75");
					return true;
				} else if (args[1].equals("2")){
					sender.sendMessage("");
					sender.sendMessage(gold + "Q: Does this server use custom plugins?");
					sender.sendMessage(yellow + "A: Yes. Everything is custom coded except for API plugins. To see a full list of all plugins our server uses type /plugins");
					return true;
				} else {
					sender.sendMessage(Messages.wrongUsage());
					return true;
				}
			} else if (args[0].equalsIgnoreCase("command") || args[0].equals("2") || args[0].equalsIgnoreCase("command")){
				if (args[1].equals("1") || args[1].equalsIgnoreCase("credits")){
					sender.sendMessage("");
					sender.sendMessage(gold + "Description" + gray + ":" + yellow + " This command shows who has made what on this server");
					sender.sendMessage(gold + "Usage" + gray + ":");
					sender.sendMessage(yellow + "/credits" + gray + " - To give a list of who did what");
					sender.sendMessage(yellow + "/credits [username]" + gray + " - To get more information about a specific user");
					sender.sendMessage(yellow + "/credits map" + gray + " - To view who built what");
					return true;
				} else if (args[1].equals("2") || args[1].equalsIgnoreCase("menu")){
					sender.sendMessage("");
					sender.sendMessage(gray + "Coming soon!");
					return true;
				} else if (args[1].equals("3") || args[1].equalsIgnoreCase("music")){
					sender.sendMessage("");
					sender.sendMessage(gray + "Coming soon!");
					return true;
				} else {
					sender.sendMessage(ChatColor.RED + "Unknown command");
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
