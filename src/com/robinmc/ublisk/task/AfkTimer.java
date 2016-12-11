package com.robinmc.ublisk.task;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.scheduler.BukkitRunnable;

import com.robinmc.ublisk.HashMaps;
import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.Ublisk;

public class AfkTimer extends BukkitRunnable {

	public static final HashMap<UUID, Integer> TIMER = new HashMap<UUID, Integer>();
	
	@Override
	public void run() {
		for (UPlayer player : Ublisk.getOnlinePlayers()){
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
