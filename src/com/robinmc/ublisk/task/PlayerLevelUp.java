package com.robinmc.ublisk.task;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.robinmc.ublisk.HashMaps;
import com.robinmc.ublisk.utils.Exp;

public class PlayerLevelUp extends BukkitRunnable {

	@Override
	public void run(){
		for (Player player : Bukkit.getOnlinePlayers()){
			int current = player.getLevel();
			int last = HashMaps.lastLevel.get(player);
			if (current > last){
				Exp.levelUp(player);
			}
			HashMaps.lastLevel.put(player, current);
		}
	}

}
