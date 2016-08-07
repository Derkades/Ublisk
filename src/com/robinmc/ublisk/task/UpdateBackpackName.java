package com.robinmc.ublisk.task;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.utils.Task;

public class UpdateBackpackName implements Task {

	@Override
	public void task(Main plugin) {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable(){
			public void run(){
				for (Player player : Bukkit.getOnlinePlayers()){
					PlayerInventory inv = player.getInventory();
					if (inv.getItemInMainHand().getType() == Material.END_CRYSTAL){
						inv.remove(Material.END_CRYSTAL);
						ItemStack item = new ItemStack(Material.END_CRYSTAL);
						ItemMeta meta = item.getItemMeta();
						meta.setDisplayName(ChatColor.BLUE + "" + ChatColor.BOLD + "Backpack");
						item.setItemMeta(meta);			
						int slot = inv.getHeldItemSlot();
						inv.setItem(slot, item);
					}
				}
			}
		}, 5*20, 5*20);
	}
	
}
