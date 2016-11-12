package com.robinmc.ublisk.commands;

import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.robinmc.ublisk.HashMaps;
import com.robinmc.ublisk.Message;
import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.UUIDUtils;
import com.robinmc.ublisk.utils.exception.PlayerNotFoundException;
import com.robinmc.ublisk.utils.perm.Permission;

public class MuteCommand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		
		if (!(sender instanceof Player)){
			sender.sendMessage(Message.NOT_A_PLAYER.get());
			return true;
		}
		
		UPlayer player = UPlayer.get(sender);
		
		if (player.hasPermission(Permission.COMMAND_MUTE)){
			player.sendMessage(Message.NO_PERMISSION.get());
			return true;
		}
		
		if (args.length == 1){
			Player target;
			try {
				target = UUIDUtils.getPlayerFromName(args[0]);
			} catch (PlayerNotFoundException e) {
				player.sendMessage(Message.PLAYER_NOT_FOUND.get());
				return true;
			}
			
			UUID uuid = target.getUniqueId();
			String targetName = target.getName();
			String playerName = player.getName();
			if (HashMaps.IS_MUTED.get(uuid)){
				player.sendMessage(Message.Complicated.Commands.Mute.unMutedOther(targetName));
				target.sendMessage(Message.Complicated.Commands.Mute.unMuted(playerName));
				HashMaps.IS_MUTED.put(uuid, false);
				return true;
			} else {
				player.sendMessage(Message.Complicated.Commands.Mute.mutedOther(targetName));
				target.sendMessage(Message.Complicated.Commands.Mute.muted(playerName));
				HashMaps.IS_MUTED.put(uuid, true);
				return true;
			}
		} else if (args.length == 2 && args[1].equalsIgnoreCase("soft")){
			Player target;
			
			try {
				target = UUIDUtils.getPlayerFromName(args[2]);
			} catch (PlayerNotFoundException e){
				player.sendMessage(Message.PLAYER_NOT_FOUND.get());
				return true;
			}
			
			UUID uuid = target.getUniqueId();
			String targetName = target.getName();
			String playerName = player.getName();
			if (HashMaps.IS_SOFT_MUTED.get(uuid)){
				player.sendMessage(Message.Complicated.Commands.Mute.unSoftMutedOther(targetName));
				target.sendMessage(Message.Complicated.Commands.Mute.unSoftMuted(playerName));
				HashMaps.IS_MUTED.put(uuid, false);
				return true;
			} else {
				player.sendMessage(Message.Complicated.Commands.Mute.softMutedOther(targetName));
				target.sendMessage(Message.Complicated.Commands.Mute.softMuted(playerName));
				HashMaps.IS_MUTED.put(uuid, true);
				return true;
			}
		} else {
			player.sendMessage(Message.WRONG_USAGE.get());
			return true;
		}
	}

}
