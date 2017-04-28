package com.robinmc.ublisk.weapons.sword;

import org.bukkit.Material;

import com.robinmc.ublisk.weapons.WeaponRarity;
import com.robinmc.ublisk.weapons.abilities.StoneCastle;

public class Purifier extends Sword {

	public Purifier() {
		super("Purifier", Material.QUARTZ_ORE, WeaponRarity.EPIC, "pretty cool sword", AttackSpeed.FAST, 10, -1, -1, new StoneCastle());
	}

}
