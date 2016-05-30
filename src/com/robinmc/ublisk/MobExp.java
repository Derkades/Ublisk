package com.robinmc.ublisk;

import org.bukkit.entity.Player;

import com.connorlinfoot.actionbarapi.ActionBarAPI;
import com.robinmc.ublisk.utils.Console;
import com.robinmc.ublisk.utils.Exp;

public class MobExp {
	
	public static void giveExp(Player player, Mob mob){
		ActionBarAPI.sendActionBar(player, "You have killed a " + mob.getName() + " and got " + mob.getExp() + " XP", 10);
		Exp.add(player, mob.getExp());
		Console.sendMessage("[MobExp] Given " + player.getName() + " " + mob.getExp() + " for killing a " + mob.getName());
	}

}
