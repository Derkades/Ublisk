package com.robinmc.ublisk.task;

import org.bukkit.Bukkit;

import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.enums.Tracker;
import com.robinmc.ublisk.utils.scheduler.Task;

public class SyncTrackers implements Task {

	@Override
	public void task(Main plugin) {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable(){
			public void run(){
				Tracker.syncAll();
			}
		}, 1*20, 1*20);
		
	}

}
