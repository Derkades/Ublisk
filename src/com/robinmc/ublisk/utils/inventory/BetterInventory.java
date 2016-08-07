package com.robinmc.ublisk.utils.inventory;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.robinmc.ublisk.utils.Console;

public class BetterInventory {
	
	private Player player;
	private Inventory inv;
	
	public BetterInventory(Player player){
		this.player = player;
		this.inv = player.getInventory();
	}
	
	public Player getPlayer(){
		return player;
	}
	
	public PlayerInventory getBukkitInventory(){
		return player.getInventory();
	}
	
	public void add(Material item, int amount){
		PlayerInventory inv = player.getInventory();
		inv.addItem(new ItemStack(item, amount));
	}
	
	public void add(Material material){
		inv.addItem(new ItemStack(material, 1));
	}
	
	public void add(ItemStack item){
		inv.addItem(item);
	}
	
	public void add(Item item){
		inv.addItem(item.getBukkitItem());
	}
	
	public void remove(ItemStack item){
		inv.remove(item);
	}
	
	public void remove(Item item){
		remove(item.getBukkitItem());
	}
	
	public void remove(Material material, int amount){
		remove(new ItemStack(material, amount));
	}
	
	public boolean contains(Material material, int amount){
		return inv.containsAtLeast(new ItemStack(material), amount);
	}
	
	public boolean containsExact(ItemStack item){
		return inv.contains(item, item.getAmount());
	}
	
	public boolean contains(ItemStack... items){
		boolean hasItems = true;
		for (ItemStack item : items){
			if (!player.getInventory().containsAtLeast(item, item.getAmount())){
				hasItems = false;
			}
		}
		return hasItems;
	}
	
	public void clear(){
		String pn = player.getName();
		Console.sendCommand("clear " + pn);
		//Because inv.clear(player); doesn't clear armour slots
	}

}
