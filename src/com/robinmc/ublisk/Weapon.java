package com.robinmc.ublisk;

import static net.md_5.bungee.api.ChatColor.*;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.tr7zw.itemnbtapi.NBTItem;
import net.md_5.bungee.api.ChatColor;

public class Weapon {
	
	public static ItemStack oldWoodenSword(){
		ItemStack item = new ItemStack(Material.WOOD_SWORD, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(GRAY + "Basic Wooden Sword");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(ChatColor.RESET + "Hi");
		meta.setLore(lore);
		meta.spigot().setUnbreakable(true);
		item.setItemMeta(meta);
		NBTItem nbt = new NBTItem(item);
		//nbt.setInteger("HideFlags", 2);
		nbt.setString("AttributeModifiers", "[{Slot:\"mainhand\",AttributeName:\"generic.attackDamage\",Name:\"generic.attackDamage\",Amount:20,Operation:0,UUIDLeast:1,UUIDMost:1}]");
		return nbt.getItem();
	}

}
