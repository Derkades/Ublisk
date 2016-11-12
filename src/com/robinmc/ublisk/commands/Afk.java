package com.robinmc.ublisk.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.robinmc.ublisk.HashMaps;
import com.robinmc.ublisk.Message;

public class Afk implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (sender instanceof Player){
			Player player = (Player) sender;
			UUID uuid = player.getUniqueId();
			String name = player.getName();
			
			if (HashMaps.AFK.get(uuid)){ //If player is already afk
				Bukkit.broadcastMessage(Message.Complicated.Commands.noLongerAfk(name)); //Set as no longer afk
				HashMaps.AFK.put(uuid, false);
				return true;
			} else { //If player is not AFK
				Bukkit.broadcastMessage(Message.Complicated.Commands.nowAfk(name)); //Set as afk
				HashMaps.AFK.put(uuid, true);
				return true;
			}
		} else {
			sender.sendMessage(Message.NOT_A_PLAYER.get());
			return true;
		}
	}

}
