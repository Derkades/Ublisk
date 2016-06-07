package com.robinmc.ublisk.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import com.robinmc.ublisk.Messages;
import com.robinmc.ublisk.Perms;

public class PlayerCommandPreprocess implements Listener {
	
	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent event){
		String cmd = event.getMessage();
		String pn = event.getPlayer().getName();
		for (Player player: Bukkit.getOnlinePlayers()){
			if (player.hasPermission(Perms.COMMAND_LOG.getPerm())){
				player.sendMessage(Messages.commandLog(pn, cmd));
			}
		}
	}

}
