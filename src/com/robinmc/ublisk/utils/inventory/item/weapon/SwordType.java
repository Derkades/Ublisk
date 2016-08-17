package com.robinmc.ublisk.utils.inventory.item.weapon;

import org.bukkit.Material;

import com.robinmc.ublisk.utils.enums.Classes;

public enum SwordType implements WeaponType {

	/**
	 * Represents a wooden sword
	 */
	WOOD(Material.WOOD_SWORD),
	
	/**
	 * Represents a stone sword
	 */
	STONE(Material.STONE_SWORD),
	
	/**
	 * Represents an iron sword
	 */
	IRON(Material.IRON_SWORD),
	
	/**
	 * Represents a gold sword
	 */
	GOLD(Material.GOLD_SWORD),
	
	/**
	 * Represents a diamond sword
	 */
	DIAMOND(Material.DIAMOND_SWORD);
	
	private Material material;	
		
	SwordType(Material material){
		this.material = material;
	}
	
	@Override
	public Material getMaterial(){
		return material;
	}

	@Override
	public Classes getClass(Classes clazz) {
		return Classes.SWORDSMAN;
	}

}
