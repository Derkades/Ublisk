package com.robinmc.ublisk.utils;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import com.robinmc.ublisk.MobExp;

public class Exp {
	
	public static void set(OfflinePlayer player, int n){
		Config.set("xp." + player.getUniqueId(), n);
	}
	
	public static void add(Player player, int n){
		Config.set("xp." + player.getUniqueId(), n + get(player));
		update(player);
	}
	
	public static int get(OfflinePlayer player){
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
	
	/**
	 * This will return a value of 0 if player is not in gamemode 2/0 or not online
	 * @param player
	 * @return Player level
	 */
	public static int getLevel(Player player){
		return player.getLevel();
	}

}
