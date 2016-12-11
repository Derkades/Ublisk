package com.robinmc.ublisk.task;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.scheduler.BukkitRunnable;

import com.robinmc.ublisk.PlayerInfo;
import com.robinmc.ublisk.Tracker;
import com.robinmc.ublisk.utils.Logger;
import com.robinmc.ublisk.utils.Logger.LogLevel;
import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.Ublisk;
import com.robinmc.ublisk.utils.guilds.Guild;
import com.robinmc.ublisk.utils.guilds.Guilds;
import com.robinmc.ublisk.utils.sql.SyncQueue;

public class AddTrackersInfoToQueue extends BukkitRunnable {

	public void run(){
		List<BukkitRunnable> list = new ArrayList<BukkitRunnable>();
		
		for (final UPlayer player : Ublisk.getOnlinePlayers()){
			for (final Tracker tracker : Tracker.values()){
				list.add(new BukkitRunnable(){
					public void run(){
						try {
							tracker.syncWithDatabase(player);
						} catch (SQLException e) {
							Logger.log(LogLevel.WARNING, "MySQL", "An error occurred while trying to synchronise.");
							e.printStackTrace();
						}
					}
				});
			}
		}
		
		for (final UPlayer player : Ublisk.getOnlinePlayers()){
			for (final PlayerInfo info : PlayerInfo.values()){
				list.add(new BukkitRunnable(){
					public void run(){
						try {
							info.syncWithDatabase(player);
						} catch (SQLException e) {
							Logger.log(LogLevel.WARNING, "MySQL", "An error occurred while trying to synchronise.");
							e.printStackTrace();
						}
					}
				});
			}
		}
		
		for (final Guild guild : Guilds.getGuilds()){
			list.add(new BukkitRunnable(){
				public void run(){
					try {
						guild.syncInfoWithDatabase();
					} catch (SQLException e){
						Logger.log(LogLevel.SEVERE, "An error occured while syncing guild info!");
						e.printStackTrace();
					}
				}
			});
		}
		
		SyncQueue.addToQueue(list);
	}
}