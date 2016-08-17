package com.robinmc.ublisk.utils.inventory.item.weapon;

import org.bukkit.Material;

import com.robinmc.ublisk.utils.enums.Classes;

public interface WeaponType {
	
	Classes getClass(Classes clazz);

	Material getMaterial();

}
