package com.robinmc.ublisk.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerLogins extends UModule {
	
	private static Map<UUID, Long> LOGIN_TIME = new HashMap<>();
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onJoin(PlayerJoinEvent event){
		UUID uuid = event.getPlayer().getUniqueId();
		long time = System.currentTimeMillis();
		LOGIN_TIME.put(uuid, time);
	}
	
	public static List<OfflinePlayer> getJoined(int minutes){
		List<OfflinePlayer> list = new ArrayList<>();
		for (Map.Entry<UUID, Long> entry : LOGIN_TIME.entrySet()){
			OfflinePlayer player = Bukkit.getOfflinePlayer(entry.getKey());
			long timeDifference = System.currentTimeMillis() - entry.getValue();
			if (timeDifference < minutes * 60 * 1000){
				list.add(player);
			}
		}
		return list;
	}

}
