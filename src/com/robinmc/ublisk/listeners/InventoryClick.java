package com.robinmc.ublisk.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import com.robinmc.ublisk.database.PlayerInfo2;
import com.robinmc.ublisk.utils.UPlayer;

public class InventoryClick implements Listener {
	
	@EventHandler(ignoreCancelled = false, priority = EventPriority.MONITOR)
	public void tracker(InventoryClickEvent event){
		UPlayer player = new UPlayer(event.getWhoClicked());
		player.tracker(PlayerInfo2.INV_CLICK);
	}
	
	@EventHandler
	public void onItemClick(InventoryClickEvent event){

		UPlayer player = new UPlayer(event.getWhoClicked());
		
		if (player.isInBuilderMode()){
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
