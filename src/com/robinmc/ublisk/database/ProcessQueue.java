package com.robinmc.ublisk.database;

import org.bukkit.scheduler.BukkitRunnable;

public class ProcessQueue extends BukkitRunnable {

	@Override
	public void run() {
		SyncQueue.syncNext();
	}

}
