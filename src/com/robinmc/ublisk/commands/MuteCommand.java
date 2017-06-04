package com.robinmc.ublisk.commands;

import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.robinmc.ublisk.HashMaps;
import com.robinmc.ublisk.Message;
import com.robinmc.ublisk.permission.Permission;
import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.exception.PlayerNotFoundException;

public class MuteCommand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		
		if (!(sender instanceof Player)){
			sender.sendMessage(Message.NOT_A_PLAYER.toString());
			return true;
		}
		
		UPlayer player = new UPlayer(sender);
		
		if (player.hasPermission(Permission.COMMAND_MUTE)){
			player.sendMessage(Message.NO_PERMISSION);
			return true;
		}
		
		if (args.length == 1){
			UPlayer target;
			try {
				target = new UPlayer(args[0]);
			} catch (PlayerNotFoundException e) {
				player.sendMessage(Message.PLAYER_NOT_FOUND);
				return true;
			}
			
			UUID uuid = target.getUniqueId();
			String targetName = target.getName();
			String playerName = player.getName();
			if (HashMaps.IS_MUTED.get(uuid)){
				player.sendPrefixedMessage("Chat", targetName + " has been unmuted.");
				target.sendPrefixedMessage("Chat", "You have been unmuted by " + playerName);
				HashMaps.IS_MUTED.put(uuid, false);
				return true;
			} else {
				player.sendPrefixedMessage("Chat", targetName + " has been muted.");
				target.sendPrefixedMessage("Chat", "You have been muted by " + playerName);
				HashMaps.IS_MUTED.put(uuid, true);
				return true;
			}
		} else if (args.length == 2 && args[0].equalsIgnoreCase("soft")){
			UPlayer target;
			
			try {
				target = new UPlayer(args[1]);
			} catch (PlayerNotFoundException e){
				player.sendMessage(Message.PLAYER_NOT_FOUND);
				return true;
			}
			
			UUID uuid = target.getUniqueId();
			String targetName = target.getName();
			String playerName = player.getName();
			if (HashMaps.IS_SOFT_MUTED.get(uuid)){
				player.sendPrefixedMessage("Chat", targetName + " has been un-soft-muted.");
				target.sendPrefixedMessage("Chat", "You have been un-soft-muted by " + playerName);
				HashMaps.IS_MUTED.put(uuid, false);
				return true;
			} else {
				player.sendPrefixedMessage("Chat", targetName + " has been soft-muted.");
				target.sendPrefixedMessage("Chat", "You have been soft-muted by " + playerName);
				HashMaps.IS_MUTED.put(uuid, true);
				return true;
			}
		} else {
			player.sendMessage(Message.WRONG_USAGE);
			return true;
		}
	}

}
