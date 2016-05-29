package com.robinmc.ublisk.utils;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class InvUtils {
	
	//TODO: Javadoc
	public void giveItem(Player player, Material item){
		PlayerInventory inv = player.getInventory();
		inv.addItem(new ItemStack(item));
	}
	
	//TODO: Javadoc
	public void giveItem(Player player, Material item, int amount){
		PlayerInventory inv = player.getInventory();
		inv.addItem(new ItemStack(item, amount));
	}
	
	public void clear(Player player){
		String pn = player.getName();
		Console.sendCommand("clear " + pn);
		//Because inv.clear(player); doesn't clear armour slots
	}

}
