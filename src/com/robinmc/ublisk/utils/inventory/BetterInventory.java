package com.robinmc.ublisk.utils.inventory;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.robinmc.ublisk.utils.quest.QuestParticipant;
import com.robinmc.ublisk.utils.weapon.Weapon;

import net.md_5.bungee.api.ChatColor;

public class BetterInventory {
	
	private PlayerInventory inv;
	
	public BetterInventory(Player player){
		this.inv = player.getInventory();
	}
	
	public BetterInventory(PlayerInventory inv){
		this.inv = inv;
	}
	
	public BetterInventory(QuestParticipant qp){
		this.inv = qp.getBukkitInventory();
	}
	
	public PlayerInventory getBukkitInventory(){
		return inv;
	}
	
	public void add(Material item, int amount){
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
			if (!inv.containsAtLeast(item, item.getAmount())){
				hasItems = false;
			}
		}
		return hasItems;
	}
	
	public void addWeapon(Weapon weapon){
		Item item = new Item(weapon.getType().getMaterial());
		item.applyNBT(weapon.getNBT());
		List<String> lore = new ArrayList<String>();
		for (String s : weapon.getLore()) lore.add(ChatColor.RESET + s);
		item.setLore(lore);
		item.setName(weapon.getName());
		inv.addItem(item.getBukkitItemStack());
	}

}
