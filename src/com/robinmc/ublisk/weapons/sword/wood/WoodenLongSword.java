package com.robinmc.ublisk.weapons.sword.wood;

import org.bukkit.Material;

import com.robinmc.ublisk.weapons.WeaponRarity;
import com.robinmc.ublisk.weapons.abilities.Meteorite;
import com.robinmc.ublisk.weapons.sword.AttackSpeed;
import com.robinmc.ublisk.weapons.sword.Sword;

public class WoodenLongSword extends Sword {

	public WoodenLongSword() {
		super("Wooden Long Sword", Material.GLASS, WeaponRarity.COMMON, "Slower than average but quite powerful", AttackSpeed.SLOW, 5,
				-1, -1, new Meteorite(50));
	}

}
