package com.robinmc.ublisk.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.NPC;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class PlayerInteract implements Listener {
	
	@EventHandler
	public void onPlayerInteractEntityEvent(PlayerInteractEntityEvent event) {
		Entity entity = event.getRightClicked();
		if (entity instanceof NPC){
			//TODO: Open menu
		}
	}

}
