package com.robinmc.ublisk.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.robinmc.ublisk.Message;

public class GuildCommand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)){
			sender.sendMessage(Message.NOT_A_PLAYER.toString());
		}
		
		return true;
	}

}
