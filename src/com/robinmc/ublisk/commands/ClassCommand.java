package com.robinmc.ublisk.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.robinmc.ublisk.Cooldown;
import com.robinmc.ublisk.Messages;
import com.robinmc.ublisk.iconmenus.ClassMenu;

public class ClassCommand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (sender instanceof Player){
			Player player = (Player) sender;
			if (Cooldown.chooseClass(player)){
				player.sendMessage(Messages.classCooldown());
				return true;
			} else {
				Cooldown.chooseClassStart(player);
				ClassMenu.open(player);
				return true;
			}
		} else {
			sender.sendMessage(Messages.noPlayer());
			return true;
		}
	}

}
