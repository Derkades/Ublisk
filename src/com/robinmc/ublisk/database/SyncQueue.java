package com.robinmc.ublisk.database;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.scheduler.BukkitRunnable;

import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.utils.Logger;
import com.robinmc.ublisk.utils.Logger.LogLevel;

public class SyncQueue {
	
	private static final List<BukkitRunnable> list = new ArrayList<BukkitRunnable>(); 
	
	public static void addToQueue(BukkitRunnable runnable){
		list.add(runnable);
	}
	
	public static void addToQueue(List<BukkitRunnable> runnableList){
		list.addAll(runnableList);
	}
	
	public static void syncNext(){
		if (list.isEmpty()){
			Logger.log(LogLevel.DEBUG, "Sync queue is empty!");
			return; //If list is empty do nothing
		} else {
			Logger.log(LogLevel.DEBUG, "Sync queue is not empty, it contains " + list.size() + " entries.");
		}
		
		BukkitRunnable runnable = list.get(0); //Get first in list
		
		try {
			runnable.runTaskAsynchronously(Main.getInstance()); //Run task asynchronously
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			list.remove(runnable); //Remove it from the list, even if an exception occurred
		}
	}
	
	public static boolean isEmpty(){
		return list.isEmpty();
	}

}
