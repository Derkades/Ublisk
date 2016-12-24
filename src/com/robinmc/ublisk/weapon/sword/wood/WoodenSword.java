package com.robinmc.ublisk.weapon.sword.wood;

import org.bukkit.Material;

import com.robinmc.ublisk.weapon.sword.Sword;

public abstract class WoodenSword extends Sword {
	
	@Override
	public Material getMaterial(){
		return Material.WOOD_SWORD;
	}

}
