package com.robinmc.ublisk;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.robinmc.ublisk.utils.Config;
import com.robinmc.ublisk.utils.Console;
import com.robinmc.ublisk.utils.EntityUtils;
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
		checkTown();
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
					if (hunger < 20){
						player.setFoodLevel(hunger + 1);
					}
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
		}, 0, 5*60*20);
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
								EntityUtils.removeMobs();
								Bukkit.broadcastMessage(Messages.removedMobs());
							}
						}, 5*20);
					}
				}, 25*20);
			}
		}, 0, 15*60*20);
	}
	
	private static void checkTown(){
		//x > 100 && x < 190 && x < 17 && x > -120
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable(){
			@SuppressWarnings("deprecation")
			public void run(){
				for (Player player: Bukkit.getOnlinePlayers()){
					for (Town town: Town.values()){
						Location loc = player.getLocation();
						if (	loc.getX() < town.lessX() &&
								loc.getX() > town.moreX() &&
								loc.getZ() < town.lessZ() &&
								loc.getZ() > town.moreZ()){
							if (!(town.getName() == Config.getString("last-town." + player.getUniqueId()))){
								player.sendTitle("", ChatColor.GRAY + "You are now in " + town.getName());
								Console.sendMessage("[Towns] " + player.getName() + " is now in " + town.getName() + " and got there from " + Config.getString("last-town." + player.getUniqueId()));
								Config.set("last-town." + player.getUniqueId(), town.getName());
							}
						}
					}
				}
			}
		}, 0, 2*20);
	}

}
