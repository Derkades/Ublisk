package com.robinmc.ublisk.task;

import org.bukkit.scheduler.BukkitRunnable;

import com.robinmc.ublisk.utils.Time;

public class FastNight extends BukkitRunnable {

	@Override
	public void run(){
		if (!Time.isDay()){
			Time.add(3L);
		}
	}

}
