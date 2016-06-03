package com.robinmc.ublisk.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.robinmc.ublisk.Helper;
import com.robinmc.ublisk.Messages;

public class Credits implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		ChatColor gold = ChatColor.GOLD;
		ChatColor yellow = ChatColor.YELLOW;
		ChatColor gray = ChatColor.GRAY;
		if (args.length == 0){
			sender.sendMessage("");
			sender.sendMessage(gold + "Plugin development" + gray + ":" + yellow + " RobinMC");
			sender.sendMessage(gold + "Building" + gray + ":" + yellow + " See /credits map");
			sender.sendMessage(gold + "Weapons" + gray + ":" + yellow + " Chaspyr");
			sender.sendMessage(gold + "Textures" + gray + ":" + yellow + " GlitcherDOTbe, RobinMC");
			sender.sendMessage(gold + "Music" + gray + ":" + yellow + " RobinMC");
			sender.sendMessage("");
			sender.sendMessage(gray + "Type /credits [user] for more information on a user");
			return true;
		} else if (args.length == 1){
			if (args[0].equals("building") || args[0].equals("map")){
				sender.sendMessage("");
				sender.sendMessage(gold + "Introduction" + gray + ":" + yellow + " RobinMC, Jerrijn, Chaspyr, EpicC4Build, SenpaiSander");
				sender.sendMessage(gold + "Gleanor" + gray + ":" + yellow + " N/A");
				return true;
			} else {
				try {
					Helper helper = Helper.fromString(args[0]);
					String ign = helper.getIgn();
					String yt = helper.getYT();
					String custom = helper.getCustom();
					sender.sendMessage("");
					sender.sendMessage(gold + "IGN" + gray + ": " + yellow + ign);
					sender.sendMessage(gold + "YouTube" + gray + ": " + yellow + yt);
					sender.sendMessage(gold + "Custom link" + gray + ": " + yellow + custom);
					return true;
				} catch (Exception e){
					sender.sendMessage(Messages.wrongUsage());
					return true;
				}
			}
		} else {
			sender.sendMessage(Messages.wrongUsage());
			return true;
		}
	}

}
