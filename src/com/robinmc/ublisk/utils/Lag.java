package com.robinmc.ublisk.utils;

import org.bukkit.scheduler.BukkitRunnable;

public class Lag extends BukkitRunnable {
	
	//Please note that this is slightly modified by me
	
	public static int tickCount = 0;
	public static long[] tick = new long[600];
	public static long lastTick = 0L;
	
	public static double getTPS(){
		return getTPS(100);
	}
	
	public static double getTPS(int ticks){
		if (tickCount< ticks) {
			return 20.0D;
		}
		
		int target = (tickCount- 1 - ticks) % tick.length;
		long elapsed = System.currentTimeMillis() - tick[target];
		
		return ticks / (elapsed / 1000.0D);
	}
	
	public static long getElapsed(int tickID){
		if (tickCount- tickID >= tick.length){
	
		}
		
		long time = tick[(tickID % tick.length)];
		return System.currentTimeMillis() - time;
	}
		
	public void run(){
		tick[(tickCount% tick.length)] = System.currentTimeMillis();
		tickCount+= 1;
	}
	
}
