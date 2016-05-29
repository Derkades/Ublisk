package com.robinmc.ublisk;

import org.bukkit.Bukkit;

import com.robinmc.ublisk.utils.Time;

public class Tasks {
	
	static Main plugin = Main.getInstance();
	
	public static void start(){
		fastNight();
	}
	
	private static void fastNight(){
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable(){
			public void run(){
				if (!Time.day()){
					Time.add(5L);
				}
			}
		}, 0, 5);
	}

}
