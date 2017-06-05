package com.robinmc.ublisk.weapons.throwable;

import org.bukkit.Material;

import com.robinmc.ublisk.weapons.Weapon;
import com.robinmc.ublisk.weapons.WeaponRarity;
import com.robinmc.ublisk.weapons.abilities.Ability;

public class Throwable extends Weapon {

	protected Throwable(String name, WeaponRarity rarity, String tagline, double movementSpeed, double knockbackResistance, Ability leftClickAbility) {
		super(name, Material.SNOW_BALL, rarity, tagline, 0, movementSpeed, knockbackResistance, leftClickAbility, null);
	}

}
