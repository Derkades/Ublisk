package com.robinmc.ublisk.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

public class InventoryClick implements Listener {
	
	@EventHandler
	public void onItemClick(InventoryClickEvent event){
		if (event.getInventory().getType() != InventoryType.PLAYER){
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
