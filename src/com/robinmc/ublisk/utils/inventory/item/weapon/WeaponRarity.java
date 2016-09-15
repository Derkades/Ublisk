package com.robinmc.ublisk.utils.inventory.item.weapon;

public enum WeaponRarity {
	
	COMMON("Common"),
	UNIQUE("Unique"),
	RARE("Rare"),
	EPIC("Epic"),
	LEGENDARY("Legendary");
	
	private String string;
	
	WeaponRarity(String string){
		this.string = string;
	}
	
	public String getName(){
		return string;
	}

}
