package com.robinmc.ublisk.weapon;

import com.robinmc.ublisk.utils.inventory.item.weapon.AttackSpeed;
import com.robinmc.ublisk.utils.inventory.item.weapon.Weapon;
import com.robinmc.ublisk.utils.inventory.item.weapon.WeaponInfo;
import com.robinmc.ublisk.utils.inventory.item.weapon.WeaponRarity;
import com.robinmc.ublisk.utils.inventory.item.weapon.type.SwordType;

public enum SwordsmanWeapon {
	
	TEST_WEAPON(new Weapon(
			SwordType.GOLD,
			WeaponRarity.LEGENDARY,
			new WeaponInfo(1.0, 1.0, 1.9, 1.0),
			"Awesome Weapon", 
			"A cool weapon")
			),
	BASIC_WOODEN_SWORD(new Weapon(
			SwordType.WOOD,
			WeaponRarity.COMMON,
			new WeaponInfo(0, 0, AttackSpeed.VERY_FAST.getSpeed(), 0),
			"Basic Wooden Sword",
			"Your handcrafted weapon")
			),
	BASIC_WOODascdEN_SWORD1(new Weapon(
			SwordType.WOOD,
			WeaponRarity.COMMON,
			new WeaponInfo(0, 0, AttackSpeed.FAST.getSpeed(), 0),
			"Basic Wooden Sword",
			"Your handcrafted weapon")
			),
	BASIC_WascdOODEN_SWORD(new Weapon(
			SwordType.WOOD,
			WeaponRarity.COMMON,
			new WeaponInfo(0, 0, AttackSpeed.MEDIUM.getSpeed(), 0),
			"Basic Wooden Sword",
			"Your handcrafted weapon")
			),
	BASIC_WOODEascdN_SWORD(new Weapon(
			SwordType.WOOD,
			WeaponRarity.COMMON,
			new WeaponInfo(0, 0, AttackSpeed.SLOW.getSpeed(), 0),
			"Basic Wooden Sword",
			"Your handcrafted weapon")
			),
	BASIC_WOODascdEN_SWORD(new Weapon(
			SwordType.WOOD,
			WeaponRarity.COMMON,
			new WeaponInfo(0, 0, AttackSpeed.VERY_SLOW.getSpeed(), 0),
			"Basic Wooden Sword",
			"Your handcrafted weapon")
			);
	
	private Weapon weapon;
	
	SwordsmanWeapon(Weapon weapon){
		this.weapon = weapon;
	}
	
	public Weapon getWeapon(){
		return weapon;
	}

}
