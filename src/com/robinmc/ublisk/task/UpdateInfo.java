package com.robinmc.ublisk.task;

import org.bukkit.Bukkit;

import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.enums.Tracker;
import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.scheduler.Scheduler;
import com.robinmc.ublisk.utils.scheduler.Task;

public class UpdateInfo implements Task {

	@Override
	public void task(Main plugin) {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable(){
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
		}, 5*20, 1*60*20);
	}

}
