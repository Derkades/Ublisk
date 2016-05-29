package com.robinmc.ublisk;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.robinmc.ublisk.utils.Console;
import com.robinmc.ublisk.utils.Exp;
import com.robinmc.ublisk.utils.Time;

public class Tasks {
	
	static Main plugin = Main.getInstance();
	
	public static void start(){
		Console.sendMessage("[Tasks] Starting all tasks...");
		fastNight();
		regenerateHunger();
		setExp();
	}
	
	private static void fastNight(){
		Console.sendMessage("[Tasks] FastNight has been started!");
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable(){
			public void run(){
				if (!Time.day()){
					Time.add(5L);
				}
			}
		}, 0, 2);
	}
	
	private static void regenerateHunger(){
		Console.sendMessage("[Tasks] RegenerateHunger has been started!");
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable(){
			public void run(){
				for (Player player: Bukkit.getOnlinePlayers()){
					int hunger = player.getFoodLevel();
					player.setFoodLevel(hunger + 1);
				}
			}
		}, 0, 5*20);
	}
	
	private static void setExp(){
		Console.sendMessage("[Tasks] SetExp has been started!");
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable(){
			public void run(){
				for (Player player: Bukkit.getOnlinePlayers()){
					int xp = Exp.get(player);
					player.setTotalExperience(xp);
				}
			}
		}, 0, 5);
	}

}
