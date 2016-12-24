package com.robinmc.ublisk.weapon.sword.wood;

import com.robinmc.ublisk.weapon.WeaponRarity;
import com.robinmc.ublisk.weapon.sword.AttackSpeed;

public class WoodenShortSword extends WoodenSword {

	@Override
	public String getName() {
		return "Wooden Short Sword";
	}

	@Override
	public WeaponRarity getRarity() {
		return WeaponRarity.COMMON;
	}

	@Override
	public String getTagLine() {
		return "Insert description here";
	}

	@Override
	public AttackSpeed getAttackSpeed() {
		return AttackSpeed.FASTEST;
	}

	@Override
	public int getDamage() {
		return 3;
	}

	@Override
	public double getMovementSpeed() {
		return -1;
	}

	@Override
	public double getKnockbackResistance() {
		return -1;
	}

}
