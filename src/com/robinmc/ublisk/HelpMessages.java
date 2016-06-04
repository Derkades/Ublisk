package com.robinmc.ublisk;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class HelpMessages {
	
	ChatColor gold = ChatColor.GOLD;
	ChatColor gray = ChatColor.GRAY;
	ChatColor yellow = ChatColor.YELLOW;
	
	public boolean main(CommandSender sender){
		sender.sendMessage("");
		sender.sendMessage(gold + "Ublisk help menu - Please choose a catagory");
		sender.sendMessage("");
		sender.sendMessage(gold + "1" + gray + ")" + yellow + " FAQ");
		sender.sendMessage(gold + "2" + gray + ")" + yellow + " Commands");
		sender.sendMessage("");
		sender.sendMessage(gray + "Type /help [number] for help regarding that catagory");
		return true;
	}
	
	public boolean faq(CommandSender sender){
		sender.sendMessage("");
		sender.sendMessage(gold + "Ublisk faq menu - Please choose a catagory");
		sender.sendMessage("");
		sender.sendMessage(gold + "1" + gray + ")" + yellow + " General");
		sender.sendMessage(gold + "2" + gray + ")" + yellow + " Plugins");
		sender.sendMessage("");
		sender.sendMessage(gray + "Now type /help faq [number]");
		return true;
	}
	
	public boolean faqGeneral(CommandSender sender){
		sender.sendMessage("");
		sender.sendMessage(gold + "Q: How can I get wood?");
		sender.sendMessage(yellow + "A: You can get wood by clicking the lever at the right side of the saw at 80 -75");
		return true;
	}
	
	public boolean faqPlugins(CommandSender sender){
		sender.sendMessage("");
		sender.sendMessage(gold + "Q: Does this server use custom plugins?");
		sender.sendMessage(yellow + "A: Yes. Everything is custom coded except for API plugins. To see a full list of all plugins our server uses type /plugins");
		return true;
	}
	 
	public boolean commands(CommandSender sender){
		sender.sendMessage("");
		sender.sendMessage(gold + "List of all commands - type /help command [command name or number]");
		sender.sendMessage("");
		sender.sendMessage(gold + "1" + gray + ")" + yellow + " Credits");
		sender.sendMessage(gold + "2" + gray + ")" + yellow + " Menu");
		sender.sendMessage(gold + "3" + gray + ")" + yellow + " Music");
		return true;
	}
	
	public boolean commandsCredits(CommandSender sender){
		sender.sendMessage("");
		sender.sendMessage(gold + "Description" + gray + ":" + yellow + " This command shows who has made what on this server");
		sender.sendMessage(gold + "Usage" + gray + ":");
		sender.sendMessage(yellow + "/credits" + gray + " - To give a list of who did what");
		sender.sendMessage(yellow + "/credits [username]" + gray + " - To get more information about a specific user");
		sender.sendMessage(yellow + "/credits map" + gray + " - To view who built what");
		return true;
	}
	
	public boolean commandsMenu(CommandSender sender){ //TODO: /menu help
		sender.sendMessage("");
		sender.sendMessage(gray + "Coming soon!");
		return true;
	}
	
	public boolean commandsMusic(CommandSender sender){ //TODO: /music help
		sender.sendMessage("");
		sender.sendMessage(gray + "Coming soon!");
		return true;
	}

}
