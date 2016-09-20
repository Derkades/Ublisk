package com.robinmc.ublisk.utils.inventory.item.weapon.type;

import org.bukkit.Material;

import com.robinmc.ublisk.enums.Clazz;
import com.robinmc.ublisk.utils.inventory.item.weapon.WeaponType;

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
	public Clazz getClass(Clazz clazz) {
		return Clazz.SWORDSMAN;
	}

}
