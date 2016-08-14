package com.robinmc.ublisk.utils.weapon;

import org.bukkit.Material;

import com.robinmc.ublisk.utils.enums.Classes;

public enum SwordType implements WeaponType {

	WOOD(Material.WOOD_SWORD),
	STONE(Material.STONE_SWORD),
	IRON(Material.IRON_SWORD),
	GOLD(Material.GOLD_SWORD),
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
