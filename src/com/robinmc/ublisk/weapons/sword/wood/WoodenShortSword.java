package com.robinmc.ublisk.weapons.sword.wood;

import org.bukkit.Material;

import com.robinmc.ublisk.weapons.WeaponRarity;
import com.robinmc.ublisk.weapons.abilities.ShieldCircle;
import com.robinmc.ublisk.weapons.sword.AttackSpeed;
import com.robinmc.ublisk.weapons.sword.Sword;

public class WoodenShortSword extends Sword {

	public WoodenShortSword() {
		super("Wooden Short Sword", Material.GLASS, WeaponRarity.COMMON, "Insert description here", AttackSpeed.FAST, 3, -1, -1, new ShieldCircle());
	}

}
