package com.robinmc.ublisk.utils;

import org.bukkit.entity.Player;

import com.robinmc.ublisk.MobExp;

public class Exp {
	
	public static void set(Player player, int n){
		Config.set("xp." + player.getUniqueId(), n);
	}
	
	public static void add(Player player, int n){
		Config.set("xp." + player.getUniqueId(), n + get(player));
	}
	
	public static int get(Player player){
		try {
			return Config.getInteger("xp." + player.getUniqueId());
		} catch (Exception e){ //If cannot get xp player doens't have value set in config yet
			set(player, 0);
			return 0;
		}
	}
	
	public static void update(Player player){
		MobExp.refreshExp(player);
	}

}
