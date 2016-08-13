package com.robinmc.ublisk.weapon;

import com.robinmc.ublisk.utils.inventory.NBT;
import com.robinmc.ublisk.utils.inventory.NBTTag;
import com.robinmc.ublisk.utils.weapon.SwordType;
import com.robinmc.ublisk.utils.weapon.Weapon;

public enum SwordsmanWeapon {
	
	TEST_WEAPON(new Weapon(SwordType.WOOD, new NBT(new NBTTag("HideFlags", 2)), "Test Weapon", "Lore line 1", "Lore line 2"));
	
	private Weapon weapon;
	
	SwordsmanWeapon(Weapon weapon){
		this.weapon = weapon;
	}
	
	public Weapon getWeapon(){
		return weapon;
	}

}
