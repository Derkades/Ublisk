package com.robinmc.ublisk.utils;

import com.robinmc.ublisk.DataFile;

@Deprecated
public class UUIDUtils {

	public static void save(UPlayer player) {
		DataFile.UUID.getConfig().set("uuid." + player.getName(), player.getUniqueId().toString());
		DataFile.UUID.getConfig().set("name." + player.getUniqueId(), player.getName());
	}

}
