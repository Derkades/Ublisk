package com.robinmc.ublisk.listeners.player;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.PlayerInventory;

import com.robinmc.ublisk.Main;

public class BreakBlock implements Listener {
	
	@EventHandler
	public void onBreakBlock(BlockBreakEvent event){
		
		if (event.isCancelled()){
			return;
		}
		
		final Block block = event.getBlock();
		final PlayerInventory inv = event.getPlayer().getInventory();
		if (block.getType() == Material.IRON_ORE && inv.getItemInMainHand().getType() != Material.BEETROOT){
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable(){
				public void run(){
					block.setType(Material.IRON_ORE);
				}
			}, 30*20);
		} else if (block.getType() == Material.HAY_BLOCK && inv.getItemInMainHand().getType() != Material.BEETROOT){
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable(){
				public void run(){
					block.setType(Material.HAY_BLOCK);
				}
			}, 10*20);
		} else if (!(event.getPlayer().getGameMode() == GameMode.CREATIVE)){
			event.setCancelled(true);
			
		}
	}

}
