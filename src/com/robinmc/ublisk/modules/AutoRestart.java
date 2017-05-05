package com.robinmc.ublisk.modules;

import org.bukkit.scheduler.BukkitRunnable;

import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.utils.Logger.LogLevel;
import com.robinmc.ublisk.utils.Ublisk;

public class AutoRestart extends UModule {
	
	private final BukkitRunnable TASK = new BukkitRunnable(){
		public void run(){
			if (Ublisk.getOnlinePlayers().length == 0){
				//If there are no online players, restart.
				AutoRestart.this.log(AutoRestart.this, LogLevel.WARNING, "Restarting server!");
				Ublisk.getServer().spigot().restart();
			} else {
				AutoRestart.this.log(AutoRestart.this, LogLevel.INFO, "Did not restart because there were players online.");
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
