package com.robinmc.ublisk.utils.sql;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.scheduler.BukkitRunnable;

import com.robinmc.ublisk.Main;

public class SyncQueue {
	
	private static final List<BukkitRunnable> list = new ArrayList<BukkitRunnable>(); 
	
	public static void addToQueue(BukkitRunnable runnable){
		list.add(runnable);
	}
	
	public static void syncNext(){
		if (list.isEmpty())
			return; //If list is empty do nothing
		
		BukkitRunnable runnable = list.get(0); //Get first in list
		
		try {
			runnable.runTaskAsynchronously(Main.getInstance()); //Run task asynchronously
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			list.remove(runnable); //Remove it from the list, even if an exception occurred
		}
	}

}
