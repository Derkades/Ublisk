package com.robinmc.ublisk.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.robinmc.ublisk.Message;
import com.robinmc.ublisk.iconmenus.ClassMenu;

@Deprecated
public class ClassCommand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (sender instanceof Player){
			Player player = (Player) sender;
			ClassMenu.open(player);
			return true;
		} else {
			sender.sendMessage(Message.NOT_A_PLAYER.get());
			return true;
		}
	}

}
