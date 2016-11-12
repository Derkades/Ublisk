package com.robinmc.ublisk.listeners.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.scheduler.Scheduler;

public class PlayerDeath implements Listener {

	@EventHandler
	public void onDeath(PlayerDeathEvent event){
		final UPlayer player = UPlayer.get(event);
		
		if (player.getLifeCrystals() > 0){
			player.removeLifeCrystals(1);
			event.setKeepInventory(true);
		} else {
			event.setKeepInventory(false);
		}
		
		Scheduler.runTaskLater(3L, new Runnable(){
			public void run(){
				player.spigot().respawn();
			}
		});
		
		Scheduler.runTaskLater(25L, new Runnable(){
			public void run(){
				player.setMana(20);
				player.setMaxHealth(20);
				player.setHealth(20);
				player.teleport(player.getTown().getSpawnLocation());
			}
		});
	}

}
