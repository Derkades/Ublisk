package com.robinmc.ublisk.weapons.sword.wood;

import com.robinmc.ublisk.weapons.WeaponRarity;
import com.robinmc.ublisk.weapons.abilities.TestAbility;
import com.robinmc.ublisk.weapons.sword.AttackSpeed;

public class WoodenShortSword extends WoodenSword {

	public WoodenShortSword() {
		super("Wooden Short Sword", WeaponRarity.COMMON, "Insert description here", AttackSpeed.FAST, 3, -1, -1, new TestAbility());
	}

}
