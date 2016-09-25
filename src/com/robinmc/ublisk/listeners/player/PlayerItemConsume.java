package com.robinmc.ublisk.listeners.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import com.robinmc.ublisk.utils.variable.Message;

public class PlayerItemConsume implements Listener {
	
	@EventHandler
	public void eat(PlayerItemConsumeEvent event){
		
		if (event.isCancelled()){
			return;
		}
		
		Player player = event.getPlayer();
		player.sendMessage(Message.CANT_EAT.get());
		event.setCancelled(true);
	}

}
