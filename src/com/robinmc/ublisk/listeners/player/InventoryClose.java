package com.robinmc.ublisk.listeners.player;

import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.Var;
import com.robinmc.ublisk.utils.Voting;

public class InventoryClose implements Listener {
	
	@EventHandler
	public void onInvClose(final InventoryCloseEvent event){
		if (event.getInventory().getName().contains("Box") && !event.getInventory().getName().contains("Shulker")){
			Voting.setPlayerOpeningBox(false);
			new BukkitRunnable(){
				public void run(){
					HumanEntity human = event.getPlayer();
					human.teleport(Voting.getOldPlayerLocation());
				}
			}.runTaskLater(Main.getInstance(), 2L);
		}
		
		if (event.getInventory().getName().contains("Loot")){
			new BukkitRunnable(){
				public void run(){
					Var.WORLD.getBlockAt(event.getInventory().getLocation()).setType(Material.AIR);
				}
			}.runTaskLater(Main.getInstance(), 5*20);
		}
	}

}
