package com.robinmc.ublisk.task;

import org.bukkit.Bukkit;
import org.bukkit.World;

import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.utils.scheduler.Task;
import com.robinmc.ublisk.utils.variable.Var;

public class ClearWeather implements Task {

	@Override
	public void task(Main plugin) {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable(){
			public void run(){
				World world = Var.WORLD;
				world.setThundering(false);
				world.setStorm(false);
			}
		}, 60*20, 5*60*20);
	}

}
