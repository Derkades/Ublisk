package com.robinmc.ublisk.weapon;

import com.robinmc.ublisk.utils.inventory.item.weapon.AttackSpeed;
import com.robinmc.ublisk.utils.inventory.item.weapon.Weapon;
import com.robinmc.ublisk.utils.inventory.item.weapon.WeaponInfo;
import com.robinmc.ublisk.utils.inventory.item.weapon.WeaponRarity;
import com.robinmc.ublisk.utils.inventory.item.weapon.type.SwordType;

public enum SwordsmanWeapon {
	
	BASIC_WOODEN_SWORD(new Weapon(
			SwordType.WOOD,
			WeaponRarity.COMMON,
			new WeaponInfo(1, -1, AttackSpeed.FASTER, -1),
			"Basic Wooden Sword", 
			"The sword you created yourself"
			)),
	WOODEN_LONG_SWORD(new Weapon(
			SwordType.WOOD,
			WeaponRarity.COMMON,
			new WeaponInfo(5, -1, AttackSpeed.VANILLA, -1),
			"Wooden Long Sword",
			"Slower than average but quite powerful"
			)),
	WOODEN_SHORT_SWORD(new Weapon(
			SwordType.WOOD,
			WeaponRarity.COMMON,
			new WeaponInfo(3, -1, AttackSpeed.FASTEST, -1),
			"Wooden Short Sword",
			"Insert description here"
			));
	
	private Weapon weapon;
	
	SwordsmanWeapon(Weapon weapon){
		this.weapon = weapon;
	}
	
	public Weapon getWeapon(){
		return weapon;
	}

}
