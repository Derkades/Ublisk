package com.robinmc.ublisk.task;

import org.bukkit.Material;
import org.bukkit.scheduler.BukkitRunnable;

import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.Ublisk;
import com.robinmc.ublisk.utils.inventory.Item;

import net.md_5.bungee.api.ChatColor;

public class LifeCrystalInventory extends BukkitRunnable {
	
	@Override
	public void run(){
		for (UPlayer player : Ublisk.getOnlinePlayers()){
			if (player.isInBuilderMode()){
				Item helmet = new Item(Material.GOLD_HELMET).setName(ChatColor.GOLD + "Builder's Helmet");
				player.getInventory().setHelmet(helmet);
			} else {
				Item menuChest = new Item(Material.CHEST)
						.setName(ChatColor.BLUE + "" + ChatColor.BOLD + "Chest")
						.setLore(ChatColor.GRAY + "Right click to open menu.", ChatColor.GRAY + "TIP: You can also use /menu")
						.setDroppable(false);
				player.getInventory().set(7, menuChest);
				
				Item item = new Item(Material.NETHER_STAR)
						.setAmount(player.getLifeCrystals())
						.setName(ChatColor.BLUE + "Life Crystals: " + ChatColor.AQUA + player.getLifeCrystals())
						.setLore("You have " + player.getLifeCrystals() + " life crystals")
						.setDroppable(false);
				player.getInventory().set(8, item);
			}
			

		}
	}

}
