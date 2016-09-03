package com.robinmc.ublisk.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.robinmc.ublisk.enums.Helper;
import com.robinmc.ublisk.utils.variable.Message;

public class Builder implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (!(sender instanceof Player)){
			sender.sendMessage(Message.NOT_A_PLAYER.get());
			return true;
		}
		
		if (args.length != 0){
			sender.sendMessage(Message.WRONG_USAGE.get());
			return true;
		}
		
		Player player = (Player) sender;
		
		if (Helper.builderModeEnabled(player)){
			Helper.disableBuilderMode(player);
			return true;
		} else {
			Helper.enableBuilderMode(player);
			return true;
		}
	}

}
