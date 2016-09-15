package com.robinmc.ublisk.utils.inventory.item.weapon;

import java.util.ArrayList;
import java.util.List;

import com.robinmc.ublisk.utils.inventory.item.ItemInfo;

import net.md_5.bungee.api.ChatColor;

public class Weapon {
	
	private WeaponType type;
	private WeaponInfo weaponInfo;
	private WeaponRarity rarity;
	private String name;
	private String lore;
	
	public Weapon(WeaponType type, WeaponRarity rarity, WeaponInfo weaponInfo, String name, String lore){
		this.type = type;
		this.weaponInfo = weaponInfo;
		this.rarity = rarity;
		this.name = name;
		this.lore = lore;
	}
	
	public WeaponType getType(){
		return type;
	}
	
	public WeaponRarity getRarity(){
		return rarity;
	}

	public WeaponInfo getWeaponInfo(){
		return weaponInfo;
	}
	
	public ItemInfo getItemInfo(){
		String color = ChatColor.WHITE + "";
		
		if (rarity == WeaponRarity.COMMON){
			color = ChatColor.GRAY + "";
		} else if (rarity == WeaponRarity.UNIQUE){
			color = ChatColor.GREEN + "";
		} else if (rarity == WeaponRarity.RARE){
			color = ChatColor.DARK_GREEN + "";
		} else if (rarity == WeaponRarity.EPIC){
			color = ChatColor.DARK_PURPLE + "" + ChatColor.BOLD;
		} else if (rarity == WeaponRarity.LEGENDARY){
			color = ChatColor.GOLD + "" + ChatColor.BOLD;
		}
		
		String name =  color + this.name;
		return new ItemInfo(name, getWeaponLore(lore));
	}
	
	private List<String> getWeaponLore(String tag){
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.WHITE + tag);
		lore.add("");
		lore.add("Damage: " + weaponInfo.getDamage());
		lore.add("Attack speed: " + weaponInfo.getAttackSpeed());
		if (weaponInfo.getMovementSpeed() != 0.7){ //If movement speed is not the default value
			lore.add("Movement speed: " + weaponInfo.getMovementSpeed());
		}
		lore.add("Knockback resistance: " + weaponInfo.getKnockbackResistance());
		return lore;
	}
	
}
