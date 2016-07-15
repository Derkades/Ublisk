package com.robinmc.ublisk.listeners;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import com.robinmc.ublisk.Main;

public class BreakBlock implements Listener {
	
	@EventHandler
	public void onBreakBlock(BlockBreakEvent event){
		
		if (event.isCancelled()){
			return;
		}
		
		final Block block = event.getBlock();
		if (block.getType() == Material.IRON_ORE){
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable(){
				public void run(){
					block.setType(Material.IRON_ORE);
				}
			}, 30*20);
		} else if (event.getPlayer().getGameMode() == GameMode.CREATIVE){
			return;
		} else {
			event.setCancelled(true);
		}
	
	}

}
