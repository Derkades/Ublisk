package com.robinmc.ublisk.utils;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import com.robinmc.ublisk.utils.exception.PlayerNotFoundException;

public class UUIDUtils {
	
	public static UUID getIdFromName(String name){
		//String string = Config.getString("uuid.uuid." + name);
		String string = DataFile.UUID.getString("uuid." + name);
		return UUID.fromString(string);
	}
	
	public static String getNameFromId(UUID uuid){
		String name = DataFile.UUID.getString("name." + uuid);
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
	
	public static OfflinePlayer getOfflinePlayerFromName(String name) throws PlayerNotFoundException{
		try {
			return Bukkit.getOfflinePlayer(getIdFromName(name));
		} catch (Exception e){
			throw new PlayerNotFoundException();
		}
	}
	
	public static Player getPlayerFromName(String name) throws PlayerNotFoundException {
		try {
			return Bukkit.getPlayer(name);
		} catch (Exception e){
			throw new PlayerNotFoundException();
		}
	}
	
	/*
	public static UUID getIdFromName(String name){
		ArrayList<String> list = new ArrayList<String>();
		list.add(name);
		UUIDFetcher uuidFetcher = new UUIDFetcher(list);
		Map<String, UUID> uuidMap = new HashMap<String, UUID>();
		
		try {
			 uuidMap = uuidFetcher.call();	
		} catch (Exception e){
			return null;
		}
		
		ArrayList<UUID> uuidList = new ArrayList<UUID>();
		
		for (UUID uuid : uuidMap.values()){
			uuidList.add(uuid);
		}
		
		return uuidList.get(0);
	}
	*/
	
	public static void save(Player player){
		DataFile.UUID.set("uuid." + player.getName(), player.getUniqueId().toString());
		DataFile.UUID.set("name." + player.getUniqueId(), player.getName());
	}

}
