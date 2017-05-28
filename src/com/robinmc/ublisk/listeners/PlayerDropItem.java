package com.robinmc.ublisk.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

import com.robinmc.ublisk.utils.UPlayer;

public class PlayerDropItem implements Listener {
	
	@EventHandler(priority = EventPriority.LOW)
	public void onItemDrop(PlayerDropItemEvent event){
		UPlayer player = new UPlayer(event);
		
		if (player.isInBuilderMode()){
			return;
		}
		
		if (event.getItemDrop().getItemStack().getType() == Material.NETHER_STAR || event.getItemDrop().getItemStack().getType() == Material.CHEST){
			event.setCancelled(true);
		}
	}
	

}
