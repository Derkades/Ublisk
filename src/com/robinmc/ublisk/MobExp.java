package com.robinmc.ublisk;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.connorlinfoot.actionbarapi.ActionBarAPI;
import com.robinmc.ublisk.utils.Console;
import com.robinmc.ublisk.utils.Exp;

public class MobExp {
	
	public static void giveExp(Player player, Mob mob){
		int xp = 0;
		if (HashMaps.doublexp.get("hi")){ //If double XP is active
			xp = 2 * mob.getExp();
			ActionBarAPI.sendActionBar(player, ChatColor.GOLD + "You have killed a " + mob.getName() + " and got " + xp + " XP", 2*10);
			Exp.add(player, xp);
			Console.sendMessage("[MobExp] Given " + player.getName() + " " + mob.getExp() + " for killing a " + mob.getName());
			refreshExp(player);
		} else {
			xp = mob.getExp();
			ActionBarAPI.sendActionBar(player, ChatColor.GREEN + "You have killed a " + mob.getName() + " and got " + xp + " XP", 2*10);
			Exp.add(player, xp);
			Console.sendMessage("[MobExp] Given " + player.getName() + " " + mob.getExp() + " for killing a " + mob.getName());
			refreshExp(player);
		}		
	}
	
	public static void refreshExp(Player player){
		int xp = Exp.get(player);
		player.setExp(0);
	    player.setLevel(0);
	    player.setTotalExperience(0);  
	    player.giveExp(xp);
	}

}
