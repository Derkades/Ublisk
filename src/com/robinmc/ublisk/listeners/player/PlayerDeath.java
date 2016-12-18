package com.robinmc.ublisk.listeners.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.utils.UPlayer;

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
		
		new BukkitRunnable(){
			public void run(){
				player.spigot().respawn();
			}
		}.runTaskLater(Main.getInstance(), 3L);
		
		new BukkitRunnable(){
			public void run(){
				player.setMana(20);
				player.setMaxHealth(20);
				player.setHealth(20);
				player.teleport(player.getTown().getSpawnLocation());
			}
		}.runTaskLater(Main.getInstance(), 25L);
	}

}
