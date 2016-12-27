package com.robinmc.ublisk.weapon.sword.wood;

import org.bukkit.Material;

import com.robinmc.ublisk.abilities.Ability;
import com.robinmc.ublisk.weapon.WeaponRarity;
import com.robinmc.ublisk.weapon.sword.AttackSpeed;
import com.robinmc.ublisk.weapon.sword.Sword;

public abstract class WoodenSword extends Sword {

	protected WoodenSword(String name, WeaponRarity rarity, String tagline, AttackSpeed attackSpeed, int damage,
			double movementSpeed, double knockbackResistance, Ability ability) {
		super(name, Material.WOOD_SWORD, rarity, tagline, attackSpeed, damage, movementSpeed, knockbackResistance, ability);
	}

}
