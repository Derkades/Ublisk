package com.robinmc.ublisk.listeners.player;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import com.robinmc.ublisk.Helper;

public class InventoryClick implements Listener {
	
	@EventHandler
	public void onItemClick(InventoryClickEvent event){
		
		if (Helper.builderModeEnabled((Player) event.getWhoClicked())){
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
