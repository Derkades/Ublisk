package com.robinmc.ublisk.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import com.robinmc.ublisk.Messages;

public class PlayerItemConsume implements Listener {
	
	@EventHandler
	public void eat(PlayerItemConsumeEvent event){
		
		if (event.isCancelled()){
			return;
		}
		
		Player player = event.getPlayer();
		player.sendMessage(Messages.cantEat(player.getName()));
		event.setCancelled(true);
	}

}
