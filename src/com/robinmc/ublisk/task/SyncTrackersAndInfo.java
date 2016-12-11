package com.robinmc.ublisk.task;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.scheduler.BukkitRunnable;

import com.robinmc.ublisk.PlayerInfo;
import com.robinmc.ublisk.Tracker;
import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.guilds.Guilds;
import com.robinmc.ublisk.utils.logging.LogLevel;
import com.robinmc.ublisk.utils.logging.Logger;
import com.robinmc.ublisk.utils.sql.SyncQueue;

public class SyncTrackersAndInfo extends BukkitRunnable {

	public void run(){
		List<BukkitRunnable> list = new ArrayList<BukkitRunnable>();
		
		for (final UPlayer player : UPlayer.getOnlinePlayers()){
			for (final PlayerInfo info : PlayerInfo.values()){
				list.add(new BukkitRunnable(){
					public void run(){
						try {
							info.syncWithDatabase(player);
						} catch (SQLException e) {
							Logger.log(LogLevel.WARNING, "MySQL", "An error occurred while trying to synchronise.");
						}
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
						try {
							tracker.syncWithDatabase(player);
						} catch (SQLException e) {
							Logger.log(LogLevel.WARNING, "MySQL", "An error occurred while trying to synchronise.");
						}
					}
				});
			}
		}
		
		SyncQueue.addToQueue(list);
	}
}