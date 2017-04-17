package com.robinmc.ublisk.modules;

import org.bukkit.scheduler.BukkitRunnable;

import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.utils.Logger;
import com.robinmc.ublisk.utils.Logger.LogLevel;
import com.robinmc.ublisk.utils.Ublisk;

public class AutoRestart extends UModule {
	
	private static final BukkitRunnable TASK = new BukkitRunnable(){
		public void run(){
			if (Ublisk.getOnlinePlayers().length == 0){
				//If there are no online players, restart.
				Logger.log(LogLevel.WARNING, "AutoRestart", "Restarting server!");
				Ublisk.getServer().spigot().restart();
			} else {
				Logger.log(LogLevel.INFO, "AutoRestart", "Did not restart because there were players online.");
			}
		}
	};
	
	@Override
	public void onEnable(Main plugin){
		TASK.runTaskTimer(plugin, 60*60*20, 60*60*20); //Run every hour
	}
	
	@Override
	public void onDisable(){
		TASK.cancel();
	}

}
