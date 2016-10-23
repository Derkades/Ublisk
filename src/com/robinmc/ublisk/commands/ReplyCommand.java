package com.robinmc.ublisk.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.robinmc.ublisk.Message;
import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.exception.LastSenderUnknownException;

public class ReplyCommand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (!(sender instanceof Player)){
			sender.sendMessage(Message.NOT_A_PLAYER.get());
			return true;
		}
		
		UPlayer player = UPlayer.get(sender);
		
		UPlayer target;
		try {
			target = player.getLastSender();
		} catch (LastSenderUnknownException e) {
			// TODO Last sender unknown message
			return true;
		}
		
		String msg = String.join("", args);
		target.sendPrivateMessage(player, msg);
		return true;
		
	}

}
