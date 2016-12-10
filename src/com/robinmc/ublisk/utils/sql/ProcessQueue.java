package com.robinmc.ublisk.utils.sql;

import org.bukkit.scheduler.BukkitRunnable;

public class ProcessQueue extends BukkitRunnable {

	@Override
	public void run() {
		SyncQueue.syncNext();
	}

}
