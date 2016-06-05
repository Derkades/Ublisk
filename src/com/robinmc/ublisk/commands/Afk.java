package com.robinmc.ublisk.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.robinmc.ublisk.HashMaps;
import com.robinmc.ublisk.Messages;

public class Afk implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (sender instanceof Player){
			Player player = (Player) sender;
			UUID uuid = player.getUniqueId();
			String name = player.getName();
			
			if (HashMaps.afk.get(uuid)){ //If player is already afk
				Bukkit.broadcastMessage(Messages.noLongerAfk(name)); //Set as no longer afk
				HashMaps.afk.put(uuid, false);
				return true;
			} else { //If player is not AFK
				Bukkit.broadcastMessage(Messages.nowAfk(name)); //Set as afk
				HashMaps.afk.put(uuid, true);
				return true;
			}
		} else {
			sender.sendMessage(Messages.noPlayer());
			return true;
		}
	}

}
