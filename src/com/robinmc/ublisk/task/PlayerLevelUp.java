package com.robinmc.ublisk.task;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.robinmc.ublisk.HashMaps;
import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.utils.Exp;
import com.robinmc.ublisk.utils.scheduler.Task;

public class PlayerLevelUp implements Task {

	@Override
	public void task(Main plugin) {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable(){
			public void run(){
				for (Player player : Bukkit.getOnlinePlayers()){
					int current = player.getLevel();
					HashMaps.lastLevel.put(player, current);
					int last = HashMaps.lastLevel.get(player);
					if (current > last){
						Exp.levelUp(player);
					}
				}
			}
		}, 0, 5*20);
	}

}
