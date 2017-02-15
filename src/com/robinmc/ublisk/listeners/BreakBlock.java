package com.robinmc.ublisk.listeners;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;

import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.Var;
import com.robinmc.ublisk.utils.Logger;
import com.robinmc.ublisk.utils.Logger.LogLevel;

import net.md_5.bungee.api.ChatColor;

public class BreakBlock implements Listener {
	
	private static final Material[] REGENERATING_MATERIALS = new Material[]{
			Material.IRON_ORE,
			Material.HAY_BLOCK
	};
	
	private static final int REGENERATE_TIME = 30;
	
	@EventHandler
	public void onBreakBlock(BlockBreakEvent event){
		
		if (event.isCancelled()){
			return;
		}
		
		final Player player = event.getPlayer();
		final PlayerInventory inv = player.getInventory();
		
		if (inv.getItemInMainHand().getType() == Material.BEETROOT && player.getGameMode() == GameMode.CREATIVE){
			player.sendMessage("Block permanently broken!");
			return;
		}
		
		final Block block = event.getBlock();
		
		for (Material regeneratingMaterial : REGENERATING_MATERIALS){
			if (block.getType() == regeneratingMaterial){
				if (!Var.BLOCK_REGENERATION_ENABLED){
					player.sendMessage(ChatColor.DARK_GREEN + "Block regeneration is disabled. This block will not turn back into its original state.");
				}
								
				Logger.log(LogLevel.INFO, "Regenerating block broken at (" + block.getX() + ", " + block.getY() + ", " + block.getZ() + ") by " + player.getName());
				
				final Material originalMaterial = block.getType();
				new BukkitRunnable(){
					public void run(){
						block.setType(originalMaterial);
					}
				}.runTaskLater(Main.getInstance(), REGENERATE_TIME * 20);
			}
		}	
	}

}
