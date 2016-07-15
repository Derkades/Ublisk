package com.robinmc.ublisk.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import com.robinmc.ublisk.CMessage;
import com.robinmc.ublisk.HashMaps;
import com.robinmc.ublisk.Perms;

public class PlayerCommandPreprocess implements Listener {
	
	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent event){
		
		if (event.isCancelled()){
			return;
		}
		
		String cmd = event.getMessage();
		Player sender = event.getPlayer();
		String pn = sender.getName();
		
		if (cmd.startsWith("/time set")){
			sender.sendMessage("Please do not use /time set. This command has been cancelled");
			event.setCancelled(true);
			return;
		}
		
		
		for (Player player: Bukkit.getOnlinePlayers()){
			if (player.hasPermission(Perms.COMMAND_LOG.getPerm())){
				if (!(player == sender)){
					if (!(HashMaps.disableCommandLog.get(player.getUniqueId()))){
						player.sendMessage(CMessage.commandLog(pn, cmd));
					}
				}
			}
		}
	}

}
