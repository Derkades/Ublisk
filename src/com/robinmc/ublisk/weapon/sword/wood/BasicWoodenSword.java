package com.robinmc.ublisk.weapon.sword.wood;

import com.robinmc.ublisk.weapon.WeaponRarity;
import com.robinmc.ublisk.weapon.sword.AttackSpeed;

public class BasicWoodenSword extends WoodenSword {

	@Override
	public String getName(){
		return "Basic Wooden Sword";
	}

	@Override
	public WeaponRarity getRarity(){
		return WeaponRarity.COMMON;
	}

	@Override
	public String getTagLine(){
		return "The sword you created yourself.";
	}

	@Override
	public int getDamage() {
		return 1;
	}

	@Override
	public double getMovementSpeed() {
		return -1;
	}

	@Override
	public AttackSpeed getAttackSpeed() {
		return AttackSpeed.FASTER;
	}

	@Override
	public double getKnockbackResistance() {
		return -1;
	}

}
