package com.robinmc.ublisk.utils;

import org.bukkit.entity.Player;

public class Exp {
	
	public static void set(Player player, int n){
		Config.set("xp." + player.getUniqueId(), n);
	}
	
	public static void add(Player player, int n){
		Config.set("xp." + player.getUniqueId(), n + get(player));
	}
	
	public static int get(Player player){
		return Config.getInteger("xp." + player.getUniqueId());
	}

}
