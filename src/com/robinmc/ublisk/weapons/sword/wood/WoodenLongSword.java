package com.robinmc.ublisk.weapons.sword.wood;

import com.robinmc.ublisk.weapons.WeaponRarity;
import com.robinmc.ublisk.weapons.abilities.Meteorite;
import com.robinmc.ublisk.weapons.sword.AttackSpeed;

public class WoodenLongSword extends WoodenSword {

	public WoodenLongSword() {
		super("Wooden Long Sword", WeaponRarity.COMMON, "Slower than average but quite powerful", AttackSpeed.SLOW, 5,
				-1, -1, new Meteorite(50));
	}

}
