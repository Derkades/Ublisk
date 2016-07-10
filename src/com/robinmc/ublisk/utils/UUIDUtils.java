package com.robinmc.ublisk.utils;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

public class UUIDUtils {
	
	public static UUID getIdFromName(String name){
		String string = Config.getString("uuid.uuid." + name);
		return UUID.fromString(string);
	}
	
	public static String getNameFromId(UUID uuid){
		String name = Config.getString("uuid.name." + uuid);
		return name;
	}
	
	public static UUID fromString(String string){
		UUID uuid = UUID.fromString(string);
		return uuid;
	}
	
	public static String getNameFromIdString(String string){
		String name = getNameFromId(fromString(string));
		return name;
	}
	
	public static OfflinePlayer getPlayerFromId(UUID uuid){
		return Bukkit.getOfflinePlayer(uuid);
	}
	
	public static OfflinePlayer getPlayerFromName(String name){
		return Bukkit.getOfflinePlayer(getIdFromName(name));
	}

}
