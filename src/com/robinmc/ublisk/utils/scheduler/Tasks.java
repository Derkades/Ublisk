package com.robinmc.ublisk.utils.scheduler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bukkit.Bukkit;

import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.task.CheckDoubleExp;
import com.robinmc.ublisk.task.CheckShield;
import com.robinmc.ublisk.task.CheckTown;
import com.robinmc.ublisk.task.ClearWeather;
import com.robinmc.ublisk.task.FastDayNight;
import com.robinmc.ublisk.task.LifeCrystalInventory;
import com.robinmc.ublisk.task.PlayerLevelUp;
import com.robinmc.ublisk.task.RandomTips;
import com.robinmc.ublisk.task.RegenerateHunger;
import com.robinmc.ublisk.task.RemoveMobs;
import com.robinmc.ublisk.task.RespawnNPC;
import com.robinmc.ublisk.task.SyncTrackers;
import com.robinmc.ublisk.task.UpdateBackpackName;
import com.robinmc.ublisk.task.UpdateDoubleExpBar;
import com.robinmc.ublisk.task.UpdateInfo;
import com.robinmc.ublisk.task.UpdateFriendsHealthBar;
import com.robinmc.ublisk.utils.logging.LogLevel;
import com.robinmc.ublisk.utils.logging.Logger;

public enum Tasks {
	
	CHECK_DOUBLE_EXP(new CheckDoubleExp()),
	CHECK_SHIELD(new CheckShield()),
	CHECK_TOWN(new CheckTown()),
	CLEAR_WEATHER(new ClearWeather()),
	FAST_DAY_NIGHT(new FastDayNight()),
	LIFE_CRYSTAL_INVENTORY(new LifeCrystalInventory()),
	LEVEL_UP(new PlayerLevelUp()),
	RANDOM_TIPS(new RandomTips()),
	REGENERATE_HUNGER(new RegenerateHunger()),
	REMOVE_MOBS(new RemoveMobs()),
	RESPAWN_NPC(new RespawnNPC()),
	SYNC_TRACKER(new SyncTrackers()),
	UPDATE_BACKPACK_NAME(new UpdateBackpackName()),
	UPDATE_DOUBLE_EXP_BAR(new UpdateDoubleExpBar()),
	UPDATE_EXP(new UpdateInfo()),
	UPDATE_FRIENDS_HEALTH_BAR(new UpdateFriendsHealthBar());
	
	private Task task;
	
	Tasks(Task task){
		this.task = task;
	}
	
	private Task getTask(){
		return task;
	}
	
	public static void start(int delay){
		Logger.log(LogLevel.INFO, "Tasks", "Starting all tasks");
		for (final Tasks tasks : Tasks.values()){
			delay = delay + 2;
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable(){
				public void run(){
					boolean success = true;
					
					Task task = tasks.getTask();
					Method method = null;
					
					try {
						method = task.getClass().getMethod("task", Main.class);
					} catch (NoSuchMethodException | SecurityException e) {
						success = false;
						e.printStackTrace();
					}
					
					try {
						method.invoke(task.getClass().newInstance(), Main.getInstance());
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						success = false;
						e.printStackTrace();
					} catch (InstantiationException e) {
						success = false;
						e.printStackTrace();
					}
					
					String taskName = task.getClass().getSimpleName();
					
					if (success){
						Logger.log(LogLevel.INFO, "Tasks", taskName + " has been successfully started!");
					} else {
						Logger.log(LogLevel.SEVERE, "Tasks", "An error occured while trying to start " + taskName + "!");
					}
				}
			}, delay);
		}
	}
}
