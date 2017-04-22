package com.robinmc.ublisk.money;

import org.bukkit.entity.Player;

import com.robinmc.ublisk.DataFile;

public class Money {
	
	public static void set(Player player, int amount){
		DataFile.MONEY.getConfig().set(player.getUniqueId().toString(), amount);
	}
	
	public static int get(Player player){
		String uuid = player.getUniqueId().toString();
		int money;
		
		if (DataFile.MONEY.getConfig().isSet(uuid)){
			money = DataFile.MONEY.getConfig().getInt(uuid);
		} else {
			money = 0;
		}
		
		return money;
	}

}
