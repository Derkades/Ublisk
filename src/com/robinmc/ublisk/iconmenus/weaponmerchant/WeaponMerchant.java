package com.robinmc.ublisk.iconmenus.weaponmerchant;

import org.bukkit.entity.Player;

public class WeaponMerchant {
	
	public static void open(String name, Player player){
		if (name.equals("Weapon Merchant - Level 1")){
			WeaponMerchant1.open(player);
		}
	}

}
