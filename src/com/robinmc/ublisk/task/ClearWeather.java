package com.robinmc.ublisk.task;

import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

import com.robinmc.ublisk.Var;

public class ClearWeather extends BukkitRunnable {

	@Override
	public void run(){
		World world = Var.WORLD;
		world.setThundering(false);
		world.setStorm(false);
	}
}
