package com.robinmc.ublisk.weapon.sword.wood;

import com.robinmc.ublisk.weapon.WeaponRarity;
import com.robinmc.ublisk.weapon.sword.AttackSpeed;

public class WoodenLongSword extends WoodenSword {

	@Override
	public String getName() {
		return "Wooden Long Sword";
	}

	@Override
	public WeaponRarity getRarity() {
		return WeaponRarity.COMMON;
	}

	@Override
	public String getTagLine() {
		return "Slower than average but quite powerful";
	}

	@Override
	public AttackSpeed getAttackSpeed() {
		return AttackSpeed.VANILLA;
	}

	@Override
	public int getDamage() {
		return 5;
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
