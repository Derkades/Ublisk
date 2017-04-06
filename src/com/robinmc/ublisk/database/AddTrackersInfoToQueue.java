package com.robinmc.ublisk.database;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.scheduler.BukkitRunnable;

import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.UUIDUtils;
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
					PlayerInfo2.syncWithDatabase(player);
				}
			});
			
			/*list.add(new BukkitRunnable(){
				public void run(){
					Tracker.syncWithDatabase(player);
				}
			});*/
			
			list.add(new BukkitRunnable(){
				public void run(){
					UUIDUtils.save(player);
				}
			});
			
			/*list.add(new BukkitRunnable(){
				public void run(){
					PlayerInfo.syncInfo(player);
				}
			});*/
		}
		
		SyncQueue.addToQueue(list);
	}
}