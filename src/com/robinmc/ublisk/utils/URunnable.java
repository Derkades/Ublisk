package com.robinmc.ublisk.utils;

import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import com.robinmc.ublisk.Main;

public abstract class URunnable {
	
	public abstract void run();
	
	private BukkitTask task;
	
	public void runTimer(long delay, long period) {
		if (task != null) throw new UnsupportedOperationException("Task has already been scheduled.");
		
		task = new BukkitRunnable(){
			public void run(){
				URunnable.this.run();
			}
		}.runTaskTimer(Main.getInstance(), delay, period);
	}
	
	public void runTimer(long period){
		runTimer(0, period);
	}
	
	public void runTimerAsync(long delay, long period){
		if (task != null) throw new UnsupportedOperationException("Task has already been scheduled.");
		
		task = new BukkitRunnable(){
			public void run(){
				URunnable.this.run();
			}
		}.runTaskTimerAsynchronously(Main.getInstance(), delay, period);
	}
	
	public void runTimerAsync(long period){
		runTimerAsync(0, period);
	}
	
	public void runLater(long delay){
		if (task != null) throw new UnsupportedOperationException("Task has already been scheduled.");
		
		task = new BukkitRunnable(){
			public void run(){
				URunnable.this.run();
			}
		}.runTaskLater(Main.getInstance(), delay);
	}
	
	public void runLaterAsync(long delay){
		if (task != null) throw new UnsupportedOperationException("Task has already been scheduled.");
		
		task = new BukkitRunnable(){
			public void run(){
				URunnable.this.run();
			}
		}.runTaskLaterAsynchronously(Main.getInstance(), delay);
	}
	
	public void runAsync(){
		if (task != null) throw new UnsupportedOperationException("Task has already been scheduled.");
		
		task = new BukkitRunnable(){
			public void run(){
				URunnable.this.run();
			}
		}.runTaskAsynchronously(Main.getInstance());
	}
	
	public void cancel(){
		task.cancel();
	}

}
