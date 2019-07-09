package xyz.derkades.ublisk.database;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.scheduler.BukkitRunnable;

import xyz.derkades.ublisk.utils.Logger;
import xyz.derkades.ublisk.utils.Logger.LogLevel;
import xyz.derkades.ublisk.utils.UPlayer;
import xyz.derkades.ublisk.utils.Ublisk;

public class AddTrackersInfoToQueue extends BukkitRunnable {

	public void run(){
		if (!SyncQueue.isEmpty())
			return;
		
		//The SyncQueue is empty, so add new stuff to it:
		
		List<BukkitRunnable> list = new ArrayList<BukkitRunnable>();
		
		for (final UPlayer player : Ublisk.getOnlinePlayers()){
			
			list.add(new BukkitRunnable(){
				public void run(){
					if (player.isAfk()){
						Logger.log(LogLevel.INFO, "PlayerInfo", "Skipping " + player.getName() + " (afk)");
						return;
					}
					
					PlayerInfo.syncWithDatabase(player);
					Logger.log(LogLevel.INFO, "PlayerInfo", "Synchronised player info for " + player.getName());
				}
			});
		}
		
		list.add(new BukkitRunnable(){
			public void run(){
				ServerInfo.syncWithDatabase();
				Logger.log(LogLevel.INFO, "Synced server info.");
			}
		});
		
		SyncQueue.addToQueue(list);
	}
}