package com.robinmc.ublisk.task;

import org.bukkit.Bukkit;

import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.utils.mob.Mob;
import com.robinmc.ublisk.utils.scheduler.Task;
import com.robinmc.ublisk.utils.variable.Message;

public class RemoveMobs implements Task {

	@Override
	public void task(final Main plugin) {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable(){
			public void run(){
				Bukkit.broadcastMessage(Message.Complicated.removeMobsWarning(30));
				Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
					public void run(){
						Bukkit.broadcastMessage(Message.Complicated.removeMobsWarning(5));
						Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
							public void run(){
								Mob.removeMobs();
								Bukkit.broadcastMessage(Message.ENTITIES_REMOVED.get());
							}
						}, 5*20);
					}
				}, 25*20);
			}
		}, 5*60*20, 15*60*20);
	}

}
