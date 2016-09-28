package com.robinmc.ublisk.task;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

public class UpdateBackpackName extends BukkitRunnable {

	@Override
	public void run(){
		// TODO Remove this when a new system is in place
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
	
}
