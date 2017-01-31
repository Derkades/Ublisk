package com.robinmc.ublisk.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.utils.UPlayer;

import net.md_5.bungee.api.ChatColor;

public class PlayerDeath implements Listener {

	@EventHandler
	public void onDeath(PlayerDeathEvent event){
		final UPlayer player = new UPlayer(event);
		
		event.setDeathMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + player.getName() + " died near " + player.getTown().getName());
		
		if (player.getLifeCrystals() > 0){
			player.setLifeCrystals(player.getLifeCrystals() - 1);
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
				player.setMaxHealth(player.getCorrectMaxHealth());
				player.setHealth(player.getCorrectMaxHealth());
				player.teleport(player.getTown().getSpawnLocation());
			}
		}.runTaskLater(Main.getInstance(), 25L);
	}

}
