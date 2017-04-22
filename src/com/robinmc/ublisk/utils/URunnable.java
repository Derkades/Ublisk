package com.robinmc.ublisk.utils;

import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import com.robinmc.ublisk.Main;

public abstract class URunnable {
	
	public abstract void run();
	
	public BukkitTask runTimer(long delay, long period) {
		return new BukkitRunnable(){
			public void run(){
				URunnable.this.run();
			}
		}.runTaskTimer(Main.getInstance(), delay, period);
	}
	
	public BukkitTask runTimer(long period){
		return runTimer(0, period);
	}
	
	public BukkitTask runTimerAsync(long delay, long period){
		return new BukkitRunnable(){
			public void run(){
				URunnable.this.run();
			}
		}.runTaskTimerAsynchronously(Main.getInstance(), delay, period);
	}
	
	public BukkitTask runTimerAsync(long period){
		return runTimerAsync(0, period);
	}
	
	public BukkitTask runLater(long delay){
		return new BukkitRunnable(){
			public void run(){
				URunnable.this.run();
			}
		}.runTaskLater(Main.getInstance(), delay);
	}
	
	public BukkitTask runLaterAsync(long delay){
		return new BukkitRunnable(){
			public void run(){
				URunnable.this.run();
			}
		}.runTaskLaterAsynchronously(Main.getInstance(), delay);
	}
	
	public BukkitTask runAsync(){
		return new BukkitRunnable(){
			public void run(){
				URunnable.this.run();
			}
		}.runTaskAsynchronously(Main.getInstance());
	}

}
