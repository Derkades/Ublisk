package xyz.derkades.ublisk.task;

import org.bukkit.scheduler.BukkitRunnable;

import xyz.derkades.ublisk.utils.Time;

public class FastNight extends BukkitRunnable {

	@Override
	public void run(){
		if (!Time.isDay()){
			Time.add(3L);
		}
	}

}
