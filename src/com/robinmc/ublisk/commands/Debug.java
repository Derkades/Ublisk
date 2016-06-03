package com.robinmc.ublisk.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.Messages;
import com.robinmc.ublisk.utils.Exp;

public class Debug implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player){
			Player player = (Player) sender;
			if (args.length == 2){
				if (args[0].equalsIgnoreCase("xp")){
					int xp = Integer.parseInt(args[1]);
					Exp.set(player, xp);
					return true;
				} else {
					player.sendMessage(Messages.wrongUsage());
					return true;
				}
			} else if (args.length == 1){
				if (args[0].equalsIgnoreCase("kill")){
					Main.removeMobs();
					Bukkit.broadcastMessage(Messages.removedMobs());
					return true;
				} else {
					player.sendMessage(Messages.wrongUsage());
					return true;
				}
			} else {
				player.sendMessage(Messages.wrongUsage());
				return true;
			}
		} else {
			sender.sendMessage(Messages.noPlayer());
			return true;
		}
	}

}
