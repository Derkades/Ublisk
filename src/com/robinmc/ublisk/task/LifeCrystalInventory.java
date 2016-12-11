package com.robinmc.ublisk.task;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.Ublisk;
import com.robinmc.ublisk.utils.inventory.BetterInventory;
import com.robinmc.ublisk.utils.inventory.item.Item;

import net.md_5.bungee.api.ChatColor;

public class LifeCrystalInventory extends BukkitRunnable {
	
	@Override
	public void run(){
		for (UPlayer player : Ublisk.getOnlinePlayers()){
			Item item = new Item(Material.NETHER_STAR);
			item.setAmount(player.getLifeCrystals());
			item.setDisplayName(ChatColor.BLUE + "Life Crystals: " + ChatColor.AQUA + player.getLifeCrystals());
			item.setLore("You have " + player.getLifeCrystals() + " life crystals");
			
			if (player.isInBuilderMode()){
				item.setMaterial(Material.WOOD_AXE);
				player.getInventory().getBukkitInventory().setHelmet(new ItemStack(Material.GOLD_HELMET));
			}
			
			BetterInventory inv = player.getInventory();
			inv.set(8, item);
		}
	}

}
