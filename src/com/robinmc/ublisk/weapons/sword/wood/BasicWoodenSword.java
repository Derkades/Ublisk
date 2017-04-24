package com.robinmc.ublisk.weapons.sword.wood;

import com.robinmc.ublisk.weapons.WeaponRarity;
import com.robinmc.ublisk.weapons.abilities.StoneCastle;
import com.robinmc.ublisk.weapons.sword.AttackSpeed;

public class BasicWoodenSword extends WoodenSword {

	public BasicWoodenSword() {
		super("Basic Wooden Sword", WeaponRarity.COMMON, "The sword you created yourself.", AttackSpeed.NORMAL, 1, -1, -1, new StoneCastle());
	}

}
