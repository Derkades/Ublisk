package com.robinmc.ublisk.task;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class RegenerateHunger extends BukkitRunnable {

	@Override
	public void run(){
		for (Player player: Bukkit.getOnlinePlayers()){
			int hunger = player.getFoodLevel();
			if (hunger < 20){
				player.setFoodLevel(hunger + 1);
			}
		}
	}	

}
