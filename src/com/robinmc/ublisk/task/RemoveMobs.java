package com.robinmc.ublisk.task;

import org.bukkit.Bukkit;

import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.utils.EntityUtils;
import com.robinmc.ublisk.utils.Task;
import com.robinmc.ublisk.utils.variable.CMessage;
import com.robinmc.ublisk.utils.variable.Message;

public class RemoveMobs implements Task {

	@Override
	public void task(final Main plugin) {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable(){
			public void run(){
				Bukkit.broadcastMessage(CMessage.removeMobsWarning(30));
				Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
					public void run(){
						Bukkit.broadcastMessage(CMessage.removeMobsWarning(5));
						Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
							public void run(){
								EntityUtils.removeMobs();
								Bukkit.broadcastMessage(Message.ENTITIES_REMOVED.get());
							}
						}, 5*20);
					}
				}, 25*20);
			}
		}, 5*60*20, 15*60*20);
	}

}
