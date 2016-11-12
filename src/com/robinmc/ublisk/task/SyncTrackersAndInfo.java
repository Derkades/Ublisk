package com.robinmc.ublisk.task;

import org.bukkit.scheduler.BukkitRunnable;

import com.robinmc.ublisk.PlayerInfo;
import com.robinmc.ublisk.Tracker;
import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.guilds.Guilds;
import com.robinmc.ublisk.utils.scheduler.Scheduler;

public class SyncTrackersAndInfo extends BukkitRunnable {

	public void run(){
		int delay = 0;
		for (final UPlayer player : UPlayer.getOnlinePlayers()){
			delay = delay + 12*20;
				
			player.refreshLastSeenDate();
			
			Scheduler.runTaskLater(delay, new Runnable(){
				public void run(){
					PlayerInfo.XP.syncWithDatabase(player);
					
					Scheduler.runTaskLater(2*20, new Runnable(){
						public void run(){
							PlayerInfo.GUILD.syncWithDatabase(player);
						}
					});
					
					Scheduler.runTaskLater(4*20, new Runnable(){
						public void run(){
							PlayerInfo.RANK.syncWithDatabase(player);
						}
					});
					
					Scheduler.runTaskLater(6*20, new Runnable(){
						public void run(){
							PlayerInfo.LAST_SEEN.syncWithDatabase(player);
						}
					});
					
					Scheduler.runTaskLater(8*20, new Runnable(){
						public void run(){
							PlayerInfo.LEVEL.syncWithDatabase(player);
						}
					});
					
					Scheduler.runTaskLater(10*20, new Runnable(){
						public void run(){
							PlayerInfo.LAST_TOWN.syncWithDatabase(player);
						}
					});
				}
			});
		}
		
		Scheduler.runTaskLater(delay + 12*20, new Runnable(){
			public void run(){
				//Sync guilds
				Guilds.syncAllGuildsWithDatabase();
			}
		});
		
		Scheduler.runTaskLater(14*20, new Runnable(){
			public void run(){
				//Sync trackers
				Tracker.syncAll();
			}
		});
	}

}
