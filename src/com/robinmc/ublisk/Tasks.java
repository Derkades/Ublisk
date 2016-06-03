package com.robinmc.ublisk;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.robinmc.ublisk.utils.Console;
import com.robinmc.ublisk.utils.Time;

public class Tasks {
	
	static Main plugin = Main.getInstance();
	
	public static void start(){
		Console.sendMessage("[Tasks] Starting all tasks...");
		fastNight();
		regenerateHunger();
		clearWeather();
		checkTnt();
		randomTip();
		removeMobs();
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
	
	private static void clearWeather(){
		Console.sendMessage("[Tasks] ClearWeather has been started!");
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable(){
			public void run(){
				Console.sendCommand("weather clear 1000000");
			}
		}, 0, 60*20);
	}
	
	private static void checkTnt(){
		Console.sendMessage("[Tasks] CheckTnt has been started!");
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable(){
			public void run(){
				for (Player player: Bukkit.getOnlinePlayers()){
					ItemStack[] inv = player.getInventory().getContents();
					for (ItemStack item:inv){
						if (!(item == null)){
							if (item.getType() == Material.TNT){
								player.sendMessage(Messages.tntDetect());
							}
						}
					}
				}
			}
		}, 0, 5*20);	
	}
	
	private static void randomTip(){
		Console.sendMessage("[Tasks] RandomTips has been started!");
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable(){
			public void run(){
				Bukkit.broadcastMessage(Tip.getRandom());
			}
		}, 0, 60*20);
	}
	
	private static void removeMobs(){
		Console.sendMessage("[Tasks] RemoveMobs has been started!");
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable(){
			public void run(){
				Bukkit.broadcastMessage(Messages.removeMobsWarning(30));
				Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
					public void run(){
						Bukkit.broadcastMessage(Messages.removeMobsWarning(5));
						Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
							public void run(){
								Main.removeMobs();
								Bukkit.broadcastMessage(Messages.removedMobs());
							}
						}, 5*20);
					}
				}, 25*20);
			}
		}, 0, 15*60*20);
	}

}
