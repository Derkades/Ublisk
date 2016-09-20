package com.robinmc.ublisk.utils.inventory.item.weapon;

import org.bukkit.Material;

import com.robinmc.ublisk.enums.Clazz;

public interface WeaponType {
	
	Clazz getClass(Clazz clazz);

	Material getMaterial();

}
