package com.robinmc.ublisk.listeners;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import com.robinmc.ublisk.utils.logging.LogLevel;
import com.robinmc.ublisk.utils.logging.Logger;

public class InventoryClick implements Listener {
	
	@EventHandler
	public void onItemClick(InventoryClickEvent event){

		Logger.log(LogLevel.DEBUG, event.getWhoClicked().getName() + " clicked in inventory " + event.getInventory().getTitle());
		
		if (event.getWhoClicked().getGameMode() == GameMode.CREATIVE){
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
