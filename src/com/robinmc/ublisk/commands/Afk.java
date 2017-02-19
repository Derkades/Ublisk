package com.robinmc.ublisk.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.robinmc.ublisk.Message;
import com.robinmc.ublisk.utils.UPlayer;

public class Afk implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (sender instanceof Player){
			UPlayer player = new UPlayer(sender);

			if (player.isAfk())
				player.setAfk(false);
			else
				player.setAfk(true);
			
			return true;
		} else {
			sender.sendMessage(Message.NOT_A_PLAYER.toString());
			return true;
		}
	}

}
