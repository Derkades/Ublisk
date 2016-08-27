package com.robinmc.ublisk.task;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.utils.LifeCrystalPlayer;
import com.robinmc.ublisk.utils.inventory.item.Item;
import com.robinmc.ublisk.utils.scheduler.Task;

import net.md_5.bungee.api.ChatColor;

public class LifeCrystalInventory implements Task {

	@Override
	public void task(Main plugin) {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable(){
			public void run(){
				for (Player player : Bukkit.getOnlinePlayers()){
					LifeCrystalPlayer life = new LifeCrystalPlayer(player);
					Item item = new Item(Material.NETHER_STAR);
					item.setAmount(life.getLifeCrystals());
					item.setDisplayName(ChatColor.BLUE + "Life Crystals: " + ChatColor.AQUA + life.getLifeCrystals());
					item.setLore("You have " + life.getLifeCrystals() + " life crystals");
					
					PlayerInventory inv = player.getInventory();
					inv.setItem(8, item.getItemStack());
				}
			}
		}, 5*20, 5*20);
	}

}
