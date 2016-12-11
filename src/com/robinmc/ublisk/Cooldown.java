package com.robinmc.ublisk;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Cooldown {
	
	public static boolean chooseClass(Player player){
		return HashMaps.COOLDOWN_CLASS.get(player.getUniqueId());
	}
	
	public static void chooseClassStart(Player player){
		final UUID uuid = player.getUniqueId();
		HashMaps.COOLDOWN_CLASS.put(uuid, true);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable(){
			public void run(){
				HashMaps.COOLDOWN_CLASS.put(uuid, false);
			}
		}, 15*60*20);
	}

}
