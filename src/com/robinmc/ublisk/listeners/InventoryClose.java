package com.robinmc.ublisk.listeners;

import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import com.robinmc.ublisk.enums.Loot;

public class InventoryClose implements Listener {
	
	@EventHandler
	public void onInvClose(InventoryCloseEvent event){
		Inventory inv = event.getInventory();
		if (inv.getType() == InventoryType.CHEST){
			Chest chest = (Chest) inv.getLocation().getBlock().getState();
			if (Loot.isLoot(chest)){
				chest.setType(Material.AIR);
			}
		}
	}

}
