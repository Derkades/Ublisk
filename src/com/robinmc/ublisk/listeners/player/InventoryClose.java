package com.robinmc.ublisk.listeners.player;

import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

import com.robinmc.ublisk.Var;
import com.robinmc.ublisk.utils.Voting;
import com.robinmc.ublisk.utils.scheduler.Scheduler;

public class InventoryClose implements Listener {
	
	@EventHandler
	public void onInvClose(final InventoryCloseEvent event){
		if (event.getInventory().getName().contains("Box")){
			Voting.setPlayerOpeningBox(false);
			Scheduler.runTaskLater(2, new Runnable(){
				public void run(){
					HumanEntity human = event.getPlayer();
					human.teleport(Voting.getOldPlayerLocation());
				}
			});
		}
		
		if (event.getInventory().getName().contains("Loot")){
			Scheduler.runTaskLater(5*20, new Runnable(){
				public void run(){
					Var.WORLD.getBlockAt(event.getInventory().getLocation()).setType(Material.AIR);
				}
			});
		}
	}

}
