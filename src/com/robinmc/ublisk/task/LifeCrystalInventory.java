package com.robinmc.ublisk.task;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.inventory.BetterInventory;
import com.robinmc.ublisk.utils.inventory.item.Item;
import com.robinmc.ublisk.utils.scheduler.Task;

import net.md_5.bungee.api.ChatColor;

public class LifeCrystalInventory implements Task {

	@Override
	public void task(Main plugin) {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable(){
			public void run(){
				for (UPlayer player : UPlayer.getOnlinePlayers()){
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
		}, 5*20, 5*20);
	}

}
