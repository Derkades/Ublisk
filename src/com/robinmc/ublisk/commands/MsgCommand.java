package com.robinmc.ublisk.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.robinmc.ublisk.HashMaps;
import com.robinmc.ublisk.enums.Setting;
import com.robinmc.ublisk.utils.Console;
import com.robinmc.ublisk.utils.MessageTarget;
import com.robinmc.ublisk.utils.UUIDUtils;
import com.robinmc.ublisk.utils.exception.NotSetException;
import com.robinmc.ublisk.utils.exception.PlayerNotFoundException;
import com.robinmc.ublisk.utils.variable.Message;

public class MsgCommand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (!(sender instanceof Player)){
			sender.sendMessage(Message.NOT_A_PLAYER.get());
			return true;
		}
		
		Player player = (Player) sender;
		
		//If player is muted don't send private message
		if (HashMaps.isMuted.get(player.getUniqueId())){
			player.sendMessage(Message.CANT_PM_MUTED.get());
			return true;
		}
		
		if (args.length >= 2){
			MessageTarget target;
			try {
				target = new MessageTarget(UUIDUtils.getPlayerFromName(args[0]));
			} catch (PlayerNotFoundException e){
				player.sendMessage(Message.PLAYER_NOT_FOUND.get());
				return true;
			}
			
			List<String> list = new ArrayList<String>();
			
			for (int x = 1; x < args.length; x++){
				list.add(args[x]);
			}
			
			String msg = String.join(" ", list);
			
			//Play a sound if the target player has enabled it
			try {
				if (Setting.PM_SOUND.get(target.getPlayer())){
					Console.sendCommand("execute " + target.getPlayer().getName() + " ~ ~ ~ playsound entity.item.pickup master @p");
				}
			} catch (NotSetException e) {	
				Setting.PM_SOUND.put(target.getPlayer(), true);
			}
			
			target.setLastSender(player);
			target.sendMessage(msg);
			return true;
		} else {
			player.sendMessage(Message.WRONG_USAGE.get());
			return true;
		}
		
	}

}
