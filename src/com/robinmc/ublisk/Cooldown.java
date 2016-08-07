package com.robinmc.ublisk;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Cooldown {
	
	public static boolean chooseClass(Player player){
		return HashMaps.cooldownClass.get(player.getUniqueId());
	}
	
	public static void chooseClassStart(Player player){
		final UUID uuid = player.getUniqueId();
		HashMaps.cooldownClass.put(uuid, true);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable(){
			public void run(){
				HashMaps.cooldownClass.put(uuid, false);
			}
		}, 14*60*20); //14 minutes to account for lag (average TPS on server is 19.5)
	}

}
