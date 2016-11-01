package com.robinmc.ublisk.task;

import org.bukkit.scheduler.BukkitRunnable;

import com.robinmc.ublisk.Tracker;

public class SyncTrackers extends BukkitRunnable {

	@Override
	public void run(){
		Tracker.syncAll();
	}

}
