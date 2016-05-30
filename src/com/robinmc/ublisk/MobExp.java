package com.robinmc.ublisk;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.connorlinfoot.actionbarapi.ActionBarAPI;
import com.robinmc.ublisk.utils.Console;
import com.robinmc.ublisk.utils.Exp;

public class MobExp {
	
	public static void giveExp(Player player, Mob mob){
		ActionBarAPI.sendActionBar(player, ChatColor.GREEN + "You have killed a " + mob.getName() + " and got " + mob.getExp() + " XP", 2*10);
		Exp.add(player, mob.getExp());
		Console.sendMessage("[MobExp] Given " + player.getName() + " " + mob.getExp() + " for killing a " + mob.getName());
	}

}
