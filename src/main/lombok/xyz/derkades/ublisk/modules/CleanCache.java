package xyz.derkades.ublisk.modules;

import org.bukkit.scheduler.BukkitRunnable;

import xyz.derkades.ublisk.Main;
import xyz.derkades.ublisk.utils.Logger;
import xyz.derkades.ublisk.utils.Logger.LogLevel;
import xyz.derkades.ublisk.utils.caching.Cache;

public class CleanCache extends UModule {
	
	public void onEnable(){
		new BukkitRunnable(){
			public void run(){
				int cleaned = Cache.cleanCache();
				Logger.log(LogLevel.INFO, "Caching", "Cleared " + cleaned + " objects from cache.");
			}
		}.runTaskTimer(Main.getInstance(), 15*20, 5*60*20);
	}

}
