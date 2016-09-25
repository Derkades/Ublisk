package com.robinmc.ublisk.listeners.player;

import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

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
	}

}
