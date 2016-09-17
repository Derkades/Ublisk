package com.robinmc.ublisk.utils.scheduler;

import org.bukkit.Bukkit;

import com.robinmc.ublisk.Main;

public class Scheduler {
	
	public static void runTaskLater(long delay, Runnable runnable){
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), runnable, delay);
	}
	
	public static void oneTickDelay(Runnable runnable){
		runTaskLater(1, runnable);
	}
	
	public static void runAsyncRepeatingTask(long start, long delay, Runnable runnable){
		Bukkit.getScheduler().runTaskTimerAsynchronously(Main.getInstance(), runnable, start, delay);
	}
	
	public static void runSyncRepeatingTask(long start, long delay, Runnable runnable){
		Bukkit.getScheduler().runTaskTimer(Main.getInstance(), runnable, start, delay);
	}
	
}
