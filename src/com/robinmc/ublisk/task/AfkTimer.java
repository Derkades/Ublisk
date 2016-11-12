package com.robinmc.ublisk.task;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.scheduler.BukkitRunnable;

import com.robinmc.ublisk.HashMaps;
import com.robinmc.ublisk.utils.UPlayer;

public class AfkTimer extends BukkitRunnable {

	public static final HashMap<UUID, Integer> TIMER = new HashMap<UUID, Integer>();
	
	@Override
	public void run() {
		for (UPlayer player : UPlayer.getOnlinePlayers()){
			UUID uuid = player.getUniqueId();
			TIMER.put(uuid, TIMER.get(uuid) + 1);
			if (TIMER.get(uuid) >= 60){
				TIMER.put(uuid, 0);
				HashMaps.AFK_MINUTES.put(uuid, HashMaps.AFK_MINUTES.get(uuid) + 1);
				if (!player.isAfk())
					player.setAfk(true);
			}
		}
	}

}
