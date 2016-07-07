package com.robinmc.ublisk;

import static net.md_5.bungee.api.ChatColor.GRAY;
import static net.md_5.bungee.api.ChatColor.GREEN;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.tr7zw.itemnbtapi.NBTItem;
import net.md_5.bungee.api.ChatColor;

public class Weapon {
	
	/*
	//Format: "minecraft:stone 1 0 {AttributeModifiers:[{Attrib...IDLeast:138836}]}"
	//So, remove the /give @p
	//Put a \ before every "
	OLD_WOODEN_SWORD(Classes.SWORDSMAN, "minecraft:wooden_sword 1 0 {HideFlags:2,display:{Name:\"Old Wooden Sword\",Lore:[\"Attack Damage: 1\"]},AttributeModifiers:[{AttributeName:\"generic.attackDamage\",Name:\"generic.attackDamage\",Amount:1,Operation:0,UUIDMost:64262,UUIDLeast:178260}]}"),
	OLD_STONE_SWORD(Classes.SWORDSMAN, "minecraft:stone_sword 1 0 {HideFlags:2,display:{Name:\"Old Stone Sword\",Lore:[\"Attack Damage: 2\"]},AttributeModifiers:[{AttributeName:\"generic.attackDamage\",Name:\"generic.attackDamage\",Amount:2,Operation:0,UUIDMost:64262,UUIDLeast:178260}]}"),
	OLD_IRON_SWORD(Classes.SWORDSMAN, "minecraft:iron_sword 1 0 {HideFlags:2,display:{Name:\"Old Iron Sword\",Lore:[\"Attack Damage: 3\"]},AttributeModifiers:[{AttributeName:\"generic.attackDamage\",Name:\"generic.attackDamage\",Amount:3,Operation:0,UUIDMost:64262,UUIDLeast:178260}]}");
	
	private Classes c;
	private String cmd;
	
	Weapon(Classes c, String cmd){
		this.c = c;
		this.cmd = cmd;
	}
	
	public Classes getClazz(){
		return c;
	}
	
	public String getCmd(){
		return cmd;
	}
	
	public static void giveWeapon(Player player, Weapon weapon){
		Console.sendCommand("give " + player.getName() + " " + weapon.getCmd());
	}
	*/
	
	public static ItemStack oldWoodenSword(){
		ItemStack item = new ItemStack(Material.WOOD_SWORD, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(GRAY + "Basic Wooden Sword");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(ChatColor.RESET + "No text here yet!");
		meta.setLore(lore);
		meta.spigot().setUnbreakable(true);
		item.setItemMeta(meta);
		NBTItem nbt = new NBTItem(item);
		nbt.setInteger("HideFlags", 2);
		return nbt.getItem();
	}

}
