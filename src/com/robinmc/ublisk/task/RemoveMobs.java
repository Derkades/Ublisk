package com.robinmc.ublisk.task;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import com.robinmc.ublisk.Message;
import com.robinmc.ublisk.mob.Mob;
import com.robinmc.ublisk.utils.scheduler.Scheduler;

public class RemoveMobs extends BukkitRunnable {

	@Override
	public void run(){
		Bukkit.broadcastMessage(Message.Complicated.removeMobsWarning(30));
		Scheduler.runTaskLater(25*20, new Runnable(){
			public void run(){
				Bukkit.broadcastMessage(Message.Complicated.removeMobsWarning(5));
				Scheduler.runTaskLater(5*20, new Runnable(){
					public void run(){
						Mob.removeMobs();
						Bukkit.broadcastMessage(Message.ENTITIES_REMOVED.get());
					}
				});
			}
		});
	}

}
