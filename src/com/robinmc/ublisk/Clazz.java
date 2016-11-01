package com.robinmc.ublisk;

import org.bukkit.entity.Player;

import com.robinmc.ublisk.utils.Config;

public enum Clazz {
	
	SWORDSMAN("Swordsman"),
	ARCHER("Archer"),
	PALADIN("Paladin"),
	SORCERER("Sorcerer");
	
	private String name;
	
	Clazz(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	public static Clazz fromString(String text) {
		for (Clazz c: Clazz.values()) {
			if (text.equalsIgnoreCase(c.name)) {
				return c;
			}
		}
		return null;
	}
	
	public static Clazz getClass(Player player){
		String name = Config.getString("class." + player.getUniqueId());
		Clazz c = fromString(name);
		return c;
	}

}
