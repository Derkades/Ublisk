package com.robinmc.ublisk;

import org.bukkit.entity.Player;

import com.robinmc.ublisk.utils.Config;

public enum Classes {
	
	SWORDSMAN("Swordsman"),
	ARCHER("Archer"),
	PALADIN("Paladin"),
	SORCERER("Sorcerer");
	
	private String name;
	
	Classes(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	public static Classes fromString(String text) {
		for (Classes c: Classes.values()) {
			if (text.equalsIgnoreCase(c.name)) {
				return c;
			}
		}
		return null;
	}
	
	public static Classes getClass(Player player){
		String name = Config.getString("class." + player.getUniqueId());
		Classes c = fromString(name);
		return c;
	}

}
