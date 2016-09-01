package com.robinmc.ublisk.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.utils.logging.LogLevel;
import com.robinmc.ublisk.utils.logging.Logger;

public class BanUtils {
	
	/**
	 * Bans a player for a given amount of time
	 * @param player
	 * @param time Time in seconds
	 */
	@SuppressWarnings("deprecation")
	public static void tempBan(final Player player, final int time){
		player.setBanned(true);
		Logger.log(LogLevel.WARNING, "Banning", player.getName() + " has been banned for " + time + " seconds.");
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable(){
			public void run(){
				player.setBanned(false);
				Logger.log(LogLevel.INFO, "Banning", player.getName() + " has been unbanned after " + time + " seconds.");
			}
		}, time * 20);
	}

}
