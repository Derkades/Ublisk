package com.robinmc.ublisk.utils.inventory.item.weapon.type;

import org.bukkit.Material;

import com.robinmc.ublisk.enums.Classes;
import com.robinmc.ublisk.utils.inventory.item.weapon.WeaponType;

public enum BowType implements WeaponType {

	/**
	 * Represents the default bow
	 */
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
