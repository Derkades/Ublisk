package com.robinmc.ublisk.listeners.player;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import com.robinmc.ublisk.Tracker;
import com.robinmc.ublisk.utils.UPlayer;

public class InventoryClick implements Listener {
	
	@EventHandler(ignoreCancelled = false, priority = EventPriority.MONITOR)
	public void tracker(InventoryClickEvent event){
		UPlayer player = UPlayer.get(event);
		player.tracker(Tracker.INV_CLICK);
	}
	
	@EventHandler
	public void onItemClick(InventoryClickEvent event){

		if (UPlayer.get(event).isInBuilderMode()){
			return;
		}
		
		if (event.getInventory().getName().contains("Box")){
			event.setCancelled(true);
			return;
		}
		
		Material[] cancel = {
				Material.NETHER_STAR,
				Material.CHEST
				};
		
		Material clicked = event.getCurrentItem().getType();
		for (Material material : cancel){
			if (clicked.equals(material)){
				event.setCancelled(true);
			}
		}
	}

}
