package com.robinmc.ublisk.weapon;

import com.robinmc.ublisk.utils.inventory.item.weapon.SwordType;
import com.robinmc.ublisk.utils.inventory.item.weapon.Weapon;
import com.robinmc.ublisk.utils.inventory.item.weapon.WeaponInfo;
import com.robinmc.ublisk.utils.inventory.item.weapon.WeaponRarity;

public enum SwordsmanWeapon {
	
	TEST_WEAPON(new Weapon(
			SwordType.GOLD,
			WeaponRarity.LEGENDARY,
			new WeaponInfo(1.0, 1.0, 1.9, 1.0),
			"Awesome Weapon", 
			"A cool weapon")
			);
	
	private Weapon weapon;
	
	SwordsmanWeapon(Weapon weapon){
		this.weapon = weapon;
	}
	
	public Weapon getWeapon(){
		return weapon;
	}

}
