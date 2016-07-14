package com.robinmc.ublisk.utils;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import com.connorlinfoot.actionbarapi.ActionBarAPI;
import com.robinmc.ublisk.HashMaps;
import com.robinmc.ublisk.Mob;

public class Exp {
	
	public static void set(OfflinePlayer player, int n){
		Config.set("xp." + player.getUniqueId(), n);
	}
	
	public static void add(Player player, int n){
		Config.set("xp." + player.getUniqueId(), n + get(player));
		refresh(player);
	}
	
	public static int get(OfflinePlayer player){
		try {
			return Config.getInteger("xp." + player.getUniqueId());
		} catch (Exception e){ //If cannot get xp player doens't have value set in config yet
			set(player, 0);
			return 0;
		}
	}
	
	/**
	 * This will return a value of 0 if player is not in gamemode 2/0 or not online
	 * @param player
	 * @return Player level
	 */
	public static int getLevel(Player player){
		return player.getLevel();
	}
	
	public static void giveMobExp(Player player, Mob mob){
		int xp = 0;
		if (HashMaps.doublexp.get("hi")){ //If double XP is active
			xp = 2 * mob.getExp();
			ActionBarAPI.sendActionBar(player, ChatColor.GOLD + "You have killed a " + mob.getName() + " and got " + xp + " XP", 2*10);
			Exp.add(player, xp);
			Console.sendMessage("[MobExp] Given " + player.getName() + " " + mob.getExp() + " for killing a " + mob.getName());
			refresh(player);
		} else {
			xp = mob.getExp();
			ActionBarAPI.sendActionBar(player, ChatColor.GREEN + "You have killed a " + mob.getName() + " and got " + xp + " XP", 2*10);
			Exp.add(player, xp);
			Console.sendMessage("[MobExp] Given " + player.getName() + " " + mob.getExp() + " for killing a " + mob.getName());
			refresh(player);
		}		
	}
	
	public static void refresh(Player player){
		int xp = Exp.get(player);
		player.setExp(0);
	    player.setLevel(0);
	    player.setTotalExperience(0);  
	    player.giveExp(xp);
	}

}
