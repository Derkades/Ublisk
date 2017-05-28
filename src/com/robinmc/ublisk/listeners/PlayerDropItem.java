package com.robinmc.ublisk.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.inventory.Item;

public class PlayerDropItem implements Listener {
	
	@EventHandler(priority = EventPriority.LOW)
	public void onItemDrop(PlayerDropItemEvent event){
		UPlayer player = new UPlayer(event);
		
		if (player.isInBuilderMode()){
			return;
		}
		
		Item item = new Item(event.getItemDrop());
		
		event.setCancelled(!item.isDroppable());
	}
	

}
