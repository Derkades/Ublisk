package com.robinmc.ublisk.utils.inventory.item.weapon.type;

import org.bukkit.Material;

import com.robinmc.ublisk.enums.Classes;
import com.robinmc.ublisk.utils.inventory.item.weapon.WeaponType;

public enum BowType implements WeaponType {

	/**
	 * Represents the default bow
	 */
	DEFAULT(Material.BOW);
	
	private Material material;
	
	BowType(Material material){
		this.material = material;
	}
	
	@Override
	public Material getMaterial(){
		return material;
	}
	
	@Override
	public Classes getClass(Classes clazz) {
		return Classes.ARCHER;
	}
	
}
