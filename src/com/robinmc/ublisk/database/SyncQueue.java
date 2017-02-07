package com.robinmc.ublisk.database;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.scheduler.BukkitRunnable;

import com.robinmc.ublisk.Main;

public class SyncQueue {
	
	private static final List<BukkitRunnable> LIST = new ArrayList<BukkitRunnable>(); 
	
	public static void addToQueue(BukkitRunnable runnable){
		LIST.add(runnable);
	}
	
	public static void addToQueue(List<BukkitRunnable> runnableList){
		LIST.addAll(runnableList);
	}
	
	public static void syncNext(){
		if (LIST.isEmpty()){
			//Logger.log(LogLevel.DEBUG, "Sync queue is empty!");
			return; //If list is empty do nothing
		} else {
			//Logger.log(LogLevel.DEBUG, "Sync queue is not empty, it contains " + LIST.size() + " entries.");
		}
		
		BukkitRunnable runnable = LIST.get(0); //Get first in list
		
		try {
			runnable.runTaskAsynchronously(Main.getInstance()); //Run task asynchronously
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			LIST.remove(runnable); //Remove it from the list, even if an exception occurred
		}
	}
	
	public static boolean isEmpty(){
		return LIST.isEmpty();
	}

}
