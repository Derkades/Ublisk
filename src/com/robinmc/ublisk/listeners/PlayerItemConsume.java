package com.robinmc.ublisk.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

public class PlayerItemConsume implements Listener {
	
	@EventHandler
	public void eat(PlayerItemConsumeEvent event){
		event.setCancelled(true);
		//TODO: Message
	}

}
