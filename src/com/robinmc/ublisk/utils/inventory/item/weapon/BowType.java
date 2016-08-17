package com.robinmc.ublisk.utils.inventory.item.weapon;

import org.bukkit.Material;

import com.robinmc.ublisk.enums.Classes;

public enum BowType implements WeaponType {

	DEFAULT {
		@Override
		public Material getMaterial(){
			return Material.BOW;
		}
	};
	
	@Override
	public Classes getClass(Classes clazz) {
		return Classes.ARCHER;
	}
	
}
