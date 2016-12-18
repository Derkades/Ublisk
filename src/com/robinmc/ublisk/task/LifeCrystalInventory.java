package com.robinmc.ublisk.task;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.Ublisk;
import com.robinmc.ublisk.utils.inventory.item.ItemBuilder;

import net.md_5.bungee.api.ChatColor;

public class LifeCrystalInventory extends BukkitRunnable {
	
	@Override
	public void run(){
		for (UPlayer player : Ublisk.getOnlinePlayers()){
			ItemStack item = new ItemBuilder(Material.NETHER_STAR)
					.setAmount(player.getLifeCrystals())
					.setName(ChatColor.BLUE + "Life Crystals: " + ChatColor.AQUA + player.getLifeCrystals())
					.setLore("You have " + player.getLifeCrystals() + " life crystals")
					.getItemStack();
			
			if (player.isInBuilderMode()){
				item.setType(Material.WOOD_AXE);
				player.getInventory().getBukkitInventory().setHelmet(new ItemStack(Material.GOLD_HELMET));
			}
			
			player.getInventory().set(8, item);
		}
	}

}
