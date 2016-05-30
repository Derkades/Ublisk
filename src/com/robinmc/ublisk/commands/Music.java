package com.robinmc.ublisk.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.robinmc.ublisk.Messages;
import com.robinmc.ublisk.iconmenus.MusicMenu;

public class Music {
	
	public class Menu implements CommandExecutor {
		
		@Override
		public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
			if (sender instanceof Player){
				Player player = (Player) sender;
				MusicMenu.open(player);
				return true;
			} else {
				sender.sendMessage(Messages.noPlayer());
				return true;
			}
		}
	}
}
