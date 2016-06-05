package com.robinmc.ublisk;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.robinmc.ublisk.utils.Console;

public class HashMaps {

	public static Map<UUID, Boolean> afk = new HashMap<UUID, Boolean>();
	
	static void resetAllPlayers(){
		for (Player p: Bukkit.getOnlinePlayers()) HashMaps.addPlayerToMaps(p);
	}
	
	public static void addPlayerToMaps(Player player){
		Console.sendMessage("[HashMaps] " + player.getName() + "'s maps has been reset");
		afk.put(player.getUniqueId(), false);
	}
	
}