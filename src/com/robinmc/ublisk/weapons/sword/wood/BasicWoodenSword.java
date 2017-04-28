package com.robinmc.ublisk.weapons.sword.wood;

import org.bukkit.Material;

import com.robinmc.ublisk.weapons.WeaponRarity;
import com.robinmc.ublisk.weapons.abilities.StoneCastle;
import com.robinmc.ublisk.weapons.sword.AttackSpeed;
import com.robinmc.ublisk.weapons.sword.Sword;

public class BasicWoodenSword extends Sword {

	public BasicWoodenSword() {
		super("Basic Wooden Sword", Material.ACTIVATOR_RAIL, WeaponRarity.COMMON, "The sword you created yourself.", AttackSpeed.NORMAL, 1, -1, -1, new StoneCastle());
	}

}
