package com.robinmc.ublisk.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.robinmc.ublisk.utils.chat.MessageTarget;
import com.robinmc.ublisk.utils.variable.Message;

public class ReplyCommand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (!(sender instanceof Player)){
			sender.sendMessage(Message.NOT_A_PLAYER.get());
			return true;
		}
		
		Player player = (Player) sender;
		@SuppressWarnings("unused")
		MessageTarget target = MessageTarget.getLastSender(player);
		
		player.sendMessage("hi!");
		
		// FIXME Finish reply command
		
		return true;
		
	}

}
