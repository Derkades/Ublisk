package com.robinmc.ublisk.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.robinmc.ublisk.HelpMessages;
import com.robinmc.ublisk.Messages;

public class Help implements CommandExecutor {
	
	HelpMessages msg = new HelpMessages();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (args.length == 0){
			return msg.main(sender);
		} else if (args.length == 1){
			if (args[0].equals("1") || args[0].equalsIgnoreCase("faq")){
				return msg.faq(sender);
			} else if (args[0].equals("2") || args[0].equalsIgnoreCase("commands")){
				return msg.commands(sender);
			} else {
				sender.sendMessage(Messages.wrongUsage());
				return true;
			}
		} else if (args.length == 2){
			if (args[0].equalsIgnoreCase("faq")){
				if (args[1].equals("1") || args[1].equalsIgnoreCase("general")){
					return msg.faqGeneral(sender);
				} else if (args[1].equals("2") || args[1].equalsIgnoreCase("commands")){
					return msg.faqPlugins(sender);
				} else {
					sender.sendMessage(Messages.wrongUsage());
					return true;
				}
			} else if (args[0].equalsIgnoreCase("command") || args[0].equals("2") || args[0].equalsIgnoreCase("command")){
				if (args[1].equals("1") || args[1].equalsIgnoreCase("credits")){
					return msg.commandsCredits(sender);
				} else if (args[1].equals("2") || args[1].equalsIgnoreCase("menu")){
					return msg.commandsMenu(sender);
				} else if (args[1].equals("3") || args[1].equalsIgnoreCase("music")){
					return msg.commandsMusic(sender);
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
