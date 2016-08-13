package com.robinmc.ublisk.utils.weapon;

import org.bukkit.Material;

import com.robinmc.ublisk.utils.enums.Classes;

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
