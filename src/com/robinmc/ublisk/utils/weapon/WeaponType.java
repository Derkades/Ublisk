package com.robinmc.ublisk.utils.weapon;

import org.bukkit.Material;

import com.robinmc.ublisk.utils.enums.Classes;

public interface WeaponType {
	
	Classes getClass(Classes clazz);

	Material getMaterial();

}
