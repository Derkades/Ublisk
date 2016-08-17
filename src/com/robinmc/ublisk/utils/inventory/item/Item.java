package com.robinmc.ublisk.utils.inventory.item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_10_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_10_R1.NBTTagCompound;

public class Item {
		
	private ItemStack item;
	
	public Item(ItemStack item){
		this.item = item;
	}
	
	public Item(Material material){
		this.item = new ItemStack(material);
	}
	
	public Material getType(){
		return item.getType();
	}
	
	public void setType(Material type){
		item.setType(type);
	}
	
	public void setMaterial(Material type){
		setType(type);
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
		meta.setDisplayName(ChatColor.RESET + name);
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
	
	public int getMaxSize(){
		return item.getMaxStackSize();
	}
	
	public int getMaxStackSize(){
		return getMaxSize();
	}
	
	public void setLore(String... lore){
		ItemMeta meta = item.getItemMeta();
		
		meta.setLore(Arrays.asList(lore));
		
		item.setItemMeta(meta);
	}
	
	public void setLore(List<String> lore){
		ItemMeta meta = item.getItemMeta();	
		meta.setLore(lore);
		item.setItemMeta(meta);
	}
	
	public List<String> getLore(){
		ItemMeta meta = item.getItemMeta();
		return meta.getLore();
	} 
	
	public void setCompound(NBTTagCompound compound){
		net.minecraft.server.v1_10_R1.ItemStack item = CraftItemStack.asNMSCopy(this.item);
		item.setTag(compound);
		this.item = CraftItemStack.asBukkitCopy(item);
	}
	
	public NBTTagCompound getCompound(){
		return CraftItemStack.asNMSCopy(item).getTag();
	}
	
	public void setItemInfo(ItemInfo info){
		List<String> lore = new ArrayList<String>();
		for (String s : info.getLore()) lore.add(ChatColor.RESET + s);
		setLore(lore);
		setName(info.getName());
	}
	
}
