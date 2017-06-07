package xyz.derkades.ublisk.weapons.throwable;

import org.bukkit.Material;

import xyz.derkades.ublisk.weapons.Weapon;
import xyz.derkades.ublisk.weapons.WeaponRarity;
import xyz.derkades.ublisk.weapons.abilities.Ability;

public class Throwable extends Weapon {

	protected Throwable(String name, WeaponRarity rarity, String tagline, double movementSpeed, double knockbackResistance, Ability leftClickAbility) {
		super(name, Material.SNOW_BALL, rarity, tagline, 0, movementSpeed, knockbackResistance, leftClickAbility, null);
	}

}
