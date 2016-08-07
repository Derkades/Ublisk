package com.robinmc.ublisk.utils.inventory;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.tr7zw.itemnbtapi.NBTItem;

public class Item {
		
	private ItemStack item;
	
	public Item(ItemStack item){
		this.item = item;
	}
	
	public Material getType(){
		return item.getType();
	}
	
	public ItemStack getItem(){
		return item;
	}
	
	public ItemStack getItemStack(){
		return item;
	}
	
	public ItemStack getBukkitItem(){
		return item;
	}
	
	public ItemStack getBukkitItemStack(){
		return item;
	}
	
	public Material getMaterial(){
		return getType();
	}
	
	public int getAmount(){
		return item.getAmount();
	}
	
	public int getSize(){
		return getAmount();
	}
	
	public void setName(String name){
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		item.setItemMeta(meta);
	}
	
	public void setDisplayName(String name){
		setName(name);
	}
	
	public String getName(){
		ItemMeta meta = item.getItemMeta();
		return meta.getDisplayName();
	}
	
	public String getDisplayName(){
		return getName();
	}
	
	public void setAmount(int amount){
		item.setAmount(amount);
	}
	
	public void setSize(int size){
		setAmount(size);
	}
	
	public void setLore(String... lore){
		ItemMeta meta = item.getItemMeta();
		
		ArrayList<String> loreList = new ArrayList<String>();
		for (String s : lore) loreList.add(s);
		
		meta.setLore(loreList);
		
		item.setItemMeta(meta);
	}
	
	public List<String> getLore(){
		ItemMeta meta = item.getItemMeta();
		return meta.getLore();
	}
	
	public void hideFlags(){
		NBTItem nbti = new NBTItem(item);
		nbti.setInteger("HideFlags", 2);
	}
	
	public boolean flagsHidden(){
		NBTItem nbti = new NBTItem(item);
		return nbti.hasKey("HideFlags");
	}
	
	public static Item fromMaterial(Material material){
		return new Item(new ItemStack(material));
	}
	
}
