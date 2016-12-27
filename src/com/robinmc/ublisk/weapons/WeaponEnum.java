package com.robinmc.ublisk.weapons;

import com.robinmc.ublisk.weapons.sword.wood.BasicWoodenSword;
import com.robinmc.ublisk.weapons.sword.wood.WoodenLongSword;
import com.robinmc.ublisk.weapons.sword.wood.WoodenShortSword;

public enum WeaponEnum {

	BASIC_WOODEN_SWORD(new BasicWoodenSword()),
	WOODEN_LONG_SWORD(new WoodenLongSword()),
	WOODEN_SHORT_SWORD(new WoodenShortSword()),

	;

	private Weapon weapon;

	WeaponEnum(Weapon weapon) {
		this.weapon = weapon;
	}

	public Weapon getWeapon() {
		return weapon;
	}

}
