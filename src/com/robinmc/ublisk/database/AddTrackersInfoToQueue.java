package com.robinmc.ublisk.database;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.scheduler.BukkitRunnable;

import com.robinmc.ublisk.utils.Logger;
import com.robinmc.ublisk.utils.Logger.LogLevel;
import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.Ublisk;

public class AddTrackersInfoToQueue extends BukkitRunnable {

	public void run(){
		if (!SyncQueue.isEmpty())
			return;
		
		//The SyncQueue is empty, so add new stuff to it:
		
		List<BukkitRunnable> list = new ArrayList<BukkitRunnable>();
		
		for (final UPlayer player : Ublisk.getOnlinePlayers()){
			
			list.add(new BukkitRunnable(){
				public void run(){
					Logger.log(LogLevel.INFO, "Synchronising player info for " + player.getName());
					PlayerInfo.syncWithDatabase(player);
				}
			});
		}
		
		SyncQueue.addToQueue(list);
	}
}