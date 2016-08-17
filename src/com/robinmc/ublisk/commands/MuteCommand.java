package com.robinmc.ublisk.commands;

import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.robinmc.ublisk.HashMaps;
import com.robinmc.ublisk.enums.Perms;
import com.robinmc.ublisk.utils.UUIDUtils;
import com.robinmc.ublisk.utils.exception.PlayerNotFoundException;
import com.robinmc.ublisk.utils.variable.CMessage;
import com.robinmc.ublisk.utils.variable.Message;

public class MuteCommand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		
		if (!(sender instanceof Player)){
			sender.sendMessage(Message.NOT_A_PLAYER.get());
			return true;
		}
		
		Player player = (Player) sender;
		
		if (!(player.hasPermission(Perms.COMMAND_MUTE.getPerm()))){
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
			if (HashMaps.isMuted.get(uuid)){
				player.sendMessage(CMessage.unMutedOther(targetName));
				target.sendMessage(CMessage.unMuted(playerName));
				HashMaps.isMuted.put(uuid, false);
				return true;
			} else {
				player.sendMessage(CMessage.mutedOther(targetName));
				target.sendMessage(CMessage.muted(playerName));
				HashMaps.isMuted.put(uuid, true);
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
			if (HashMaps.isSoftMuted.get(uuid)){
				player.sendMessage(CMessage.unSoftMutedOther(targetName));
				target.sendMessage(CMessage.unSoftMuted(playerName));
				HashMaps.isMuted.put(uuid, false);
				return true;
			} else {
				player.sendMessage(CMessage.softMutedOther(targetName));
				target.sendMessage(CMessage.softMuted(playerName));
				HashMaps.isMuted.put(uuid, true);
				return true;
			}
		} else {
			player.sendMessage(Message.WRONG_USAGE.get());
			return true;
		}
	}

}
