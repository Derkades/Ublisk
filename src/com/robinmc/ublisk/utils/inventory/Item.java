package com.robinmc.ublisk.utils.inventory;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_11_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import net.minecraft.server.v1_11_R1.NBTBase;
import net.minecraft.server.v1_11_R1.NBTTagCompound;

public class Item {
	
	public ItemStack item;
	
	public Item(ItemStack item){
		this.item = item;
	}
	
	public Item(String skullOwner){
		item = new ItemBuilder(Material.SKULL_ITEM).setDamage(3).setSkullOwner(skullOwner).getItemStack();
	}
	
	public Item setAmount(int amount){
		item.setAmount(amount);
		return this;
	}
	
	public int getAmount(){
		return item.getAmount();
	}
	
	public Item setDamage(int i){
		item.setDurability((short) i);
		return this;
	}
	
	public Item setName(String name){
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		item.setItemMeta(meta);
		return this;
	}
	
	public Item setLore(String... lore){
		return this.setLore(Arrays.asList(lore));
	}
	
	public Item setLore(List<String> lore){
		ItemMeta meta = item.getItemMeta();
		meta.setLore(lore);
		item.setItemMeta(meta);
		return this;
	}
	
	public Item setSkullOwner(String playerName){
		if (item.getItemMeta() instanceof SkullMeta){
			SkullMeta meta = (SkullMeta) item.getItemMeta();
			meta.setOwner(playerName);
			item.setItemMeta(meta);
			return this;
		} throw new UnsupportedOperationException("Item is not a player skull");
	}
	
	public Item setLeatherArmorColor(Color color){
		if (item.getItemMeta() instanceof LeatherArmorMeta){
			LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
			meta.setColor(color);
			return this;
		} else {
			throw new UnsupportedOperationException("Item is not leather armor.");
		}
	}
	
	public NBTTagCompound getNBT(){
		net.minecraft.server.v1_11_R1.ItemStack nms = CraftItemStack.asNMSCopy(item);
		return nms.getTag();
	}
	
	public Item setNBT(NBTTagCompound nbtTagCompound){
		net.minecraft.server.v1_11_R1.ItemStack nms = CraftItemStack.asNMSCopy(item);
		nms.setTag(nbtTagCompound);
		item = CraftItemStack.asBukkitCopy(nms);
		return this;
	}
	
	public Item setNBTValue(String key, NBTBase value){
		NBTTagCompound nbt = this.getNBT();
		nbt.set(key, value);
		this.setNBT(nbt);
		return this;
	}
	
	public Material getType(){
		return item.getType();
	}
	
	public ItemStack getItemStack(){
		return item;
	}
	
	@Override
	public String toString(){
		return "Item[" + item.getType() + "]"; 
	}

}
