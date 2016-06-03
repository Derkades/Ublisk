package com.robinmc.ublisk.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.robinmc.ublisk.Main;

public class BanUtils {
	
	@SuppressWarnings("deprecation")
	public static void tempBan(final Player player, final int time){
		player.setBanned(true);
		Console.sendMessage("[Banning] " + player.getName() + " has been banned for " + time + " seconds.");
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable(){
			public void run(){
				player.setBanned(false);
				Console.sendMessage("[Banning] " + player.getName() + " has been unbanned after " + time + " seconds.");
			}
		}, time * 20);
	}

}
