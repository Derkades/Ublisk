package com.robinmc.ublisk.utils.enums;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bukkit.Bukkit;

import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.task.CheckDoubleExp;
import com.robinmc.ublisk.task.CheckShield;
import com.robinmc.ublisk.task.CheckTown;
import com.robinmc.ublisk.task.ClearWeather;
import com.robinmc.ublisk.task.FastNight;
import com.robinmc.ublisk.task.RandomTips;
import com.robinmc.ublisk.task.RegenerateHunger;
import com.robinmc.ublisk.task.RemoveMobs;
import com.robinmc.ublisk.task.RespawnNPC;
import com.robinmc.ublisk.task.UpdateBackpackName;
import com.robinmc.ublisk.task.UpdateDoubleExpBar;
import com.robinmc.ublisk.task.UpdateExp;
import com.robinmc.ublisk.task.UpdateFriendsHealthBar;
import com.robinmc.ublisk.utils.Console;
import com.robinmc.ublisk.utils.Task;

public enum Tasks {
	
	CHECK_DOUBLE_EXP(new CheckDoubleExp()),
	CHECK_SHIELD(new CheckShield()),
	CHECK_TOWN(new CheckTown()),
	CLEAR_WEATHER(new ClearWeather()),
	FAST_NIGHT(new FastNight()),
	RANDOM_TIPS(new RandomTips()),
	REGENERATE_HUNGER(new RegenerateHunger()),
	REMOVE_MOBS(new RemoveMobs()),
	RESPAWN_NPC(new RespawnNPC()),
	UPDATE_BACKPACK_NAME(new UpdateBackpackName()),
	UPDATE_DOUBLE_EXP_BAR(new UpdateDoubleExpBar()),
	UPDATE_EXP(new UpdateExp()),
	UPDATE_FRIENDS_HEALTH_BAR(new UpdateFriendsHealthBar());
	
	private Task task;
	
	Tasks(Task task){
		this.task = task;
	}
	
	private Task getTask(){
		return task;
	}
	
	public static void start(int delay){
		Console.sendMessage("[Tasks] Starting all tasks...");
		for (final Tasks tasks : Tasks.values()){
			delay = delay + 5;
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
						Console.sendMessage("[Tasks] " + taskName + " has been successfully started!");
					} else {
						Console.sendMessage("[Tasks] An error occured while trying to start " + taskName + "!");
					}
				}
			}, delay);
		}
	}
}
