package com.robinmc.ublisk.task;

import org.bukkit.scheduler.BukkitRunnable;

import com.robinmc.ublisk.enums.Tracker;
import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.scheduler.Scheduler;

public class UpdateInfo extends BukkitRunnable {

	public void run(){
		int delay = 0;
		for (final UPlayer player : UPlayer.getOnlinePlayers()){
			delay = delay + 10*20;
				
			player.refreshLastSeenDate();
			
			Scheduler.runTaskLater(delay, new Runnable(){
				public void run(){
					Tracker.PlayerInfo.syncExp(player);
					
					Scheduler.runTaskLater(2*20, new Runnable(){
						public void run(){
							Tracker.PlayerInfo.syncGuild(player);
						}
					});
					
					Scheduler.runTaskLater(4*20, new Runnable(){
						public void run(){
							Tracker.PlayerInfo.syncRank(player);
						}
					});
					
					Scheduler.runTaskLater(6*20, new Runnable(){
						public void run(){
							Tracker.PlayerInfo.syncLastSeen(player);
						}
					});
					
					Scheduler.runTaskLater(8*20, new Runnable(){
						public void run(){
							//Nothing yet
						}
					});
				}
			});
		}
	}

}
