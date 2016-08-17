package com.robinmc.ublisk.task;

import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.utils.scheduler.Task;

public class RespawnNPC implements Task {

	@Override
	public void task(final Main plugin) {
		/*
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable(){
			public void run(){
				final NPCUtils api = new NPCUtils();
				api.despawnAll();
				Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
					public void run(){
						Console.sendMessage("[NPC] All NPCs have been respawned!");
						api.spawnAll();
					}
				}, 10);
			}
		}, 5*60*20, 5*60*20);
		*/
	}

}
