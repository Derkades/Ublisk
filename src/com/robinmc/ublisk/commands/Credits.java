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
		/*
		ChatColor gold = ChatColor.GOLD;
		ChatColor yellow = ChatColor.YELLOW;
		ChatColor gray = ChatColor.GRAY;
		if (args.length == 0){
			sender.sendMessage("");
			sender.sendMessage(gold + "Plugin development" + gray + ":" + yellow + " RobinMC");
			sender.sendMessage(gold + "Building" + gray + ":" + yellow + " See /credits map");
			sender.sendMessage(gold + "Weapons" + gray + ":" + yellow + " RobinMC, SenpaiSander, Chaspyr");
			sender.sendMessage(gold + "Textures" + gray + ":" + yellow + " GlitcherDOTbe, RobinMC, RottenNugget, SenpaiSander, Breecko, Jerrijn, Xisuma");
			sender.sendMessage(gold + "Music" + gray + ":" + yellow + " RobinMC, SirSydan");
			sender.sendMessage(gold + "Quests" + gray + ": " + yellow + "SenpaiSander, RobinMC");
			sender.sendMessage(gold + "Bug testing" + gray + ": " + yellow + "Chaspyr, RobinMC");
			sender.sendMessage("");
			sender.sendMessage(gray + "Type /credits [user] for more information on a user");
			return true;
		} else if (args.length == 1){
			if (args[0].equals("building") || args[0].equals("map")){
				sender.sendMessage("");
				sender.sendMessage(gold + "All builders (in order of date)" + gray + ": " + yellow + "RobinMC, Chaspyr, Sander_Gaming, Jerrijn, SenpaiSander, EpicC4Build, LarissaMC, MrParkerl11");
				sender.sendMessage("");
				sender.sendMessage(gold + "Non-town areas" + gray + ": " + yellow + "RobinMC, LarissaMC, SenpaiSander, Sander_Gaming, Jerrijn");
				sender.sendMessage(gold + "Landscaping" + gray + ": " + yellow + "RobinMC, LarissaMC, Jerrijn, SenpaiSander");
				sender.sendMessage(gold + "Introduction" + gray + ": " + yellow + "RobinMC, Jerrijn, Chaspyr, EpicC4Build, SenpaiSander");
				sender.sendMessage(gold + "Gleanor" + gray + ": " + yellow + "Jerrijn, Chaspyr, SenpaiSander");
				sender.sendMessage(gold + "Rhocus" + gray + ": " + yellow + "Jerrijn, RobinMC, EpicC4Build, LarissaMC");
				sender.sendMessage(gold + "Desert area" + gray + ": " + yellow + "LarissaMC, MrParkerl11, RobinMC, SenpaiSander");
				return true;
			} else {
				try {
					Helper helper = Helper.fromString(args[0]);
					String ign = helper.getIgn();
					String yt = helper.getYT();
					String custom = helper.getCustom();
					
					if (yt == ""){
						yt = "N/A";
					}
					
					if (custom == ""){
						custom = "N/A";
					}
					
					sender.sendMessage("");
					sender.sendMessage(gold + "IGN" + gray + ": " + yellow + ign);
					sender.sendMessage(gold + "YouTube" + gray + ": " + yellow + yt);
					sender.sendMessage(gold + "Custom link" + gray + ": " + yellow + custom);
					return true;
				} catch (Exception e){
					sender.sendMessage(Message.Complicated.Commands.userNotFound(args[0]));
					return true;
				}
			}
		} else {
			sender.sendMessage(Message.WRONG_USAGE.get());
			return true;
		}
		*/
	}

}
