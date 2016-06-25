package com.robinmc.ublisk.utils;

import java.util.UUID;

public class UUIDUtils {
	
	public static UUID getIdFromName(String name){
		String string = Config.getString("uuid.uuid." + name);
		return UUID.fromString(string);
	}
	
	public static String getNameFromId(UUID uuid){
		String name = Config.getString("uuid.name." + uuid);
		return name;
	}

}
