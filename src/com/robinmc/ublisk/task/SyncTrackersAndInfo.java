package com.robinmc.ublisk.task;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.scheduler.BukkitRunnable;

import com.robinmc.ublisk.PlayerInfo;
import com.robinmc.ublisk.Tracker;
import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.guilds.Guilds;
import com.robinmc.ublisk.utils.sql.SyncQueue;

public class SyncTrackersAndInfo extends BukkitRunnable {

	public void run(){
		List<BukkitRunnable> list = new ArrayList<BukkitRunnable>();
		for (final UPlayer player : UPlayer.getOnlinePlayers()){
			for (final PlayerInfo info : PlayerInfo.values()){
				list.add(new BukkitRunnable(){
					public void run(){
						info.syncWithDatabase(player);
					}
				});
			}
		}
		
		list.add(new BukkitRunnable(){
			public void run() {
				Guilds.syncAllGuildsWithDatabase();
			}
		});
		
		for (final UPlayer player : UPlayer.getOnlinePlayers()){
			for (final Tracker tracker : Tracker.values()){
				list.add(new BukkitRunnable(){
					public void run(){
						tracker.syncWithDatabase(player);
					}
				});
			}
		}
		
		SyncQueue.addToQueue(list);
		
		/* XXX Remove this old code
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
		*/
	}

}
