package com.robinmc.ublisk;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.robinmc.ublisk.utils.Config;
import com.robinmc.ublisk.utils.Console;
import com.robinmc.ublisk.utils.EntityUtils;
import com.robinmc.ublisk.utils.Exp;
import com.robinmc.ublisk.utils.Time;

public class Tasks {
	
	static Main plugin = Main.getInstance();
	
	public static void start(){
		Console.sendMessage("[Tasks] Starting all tasks...");
		fastNight();
		regenerateHunger();
		clearWeather();
		randomTip();
		removeMobs();
		checkTown();
		respawnNpcs();
		updateExp();
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
		}, 5*60*20, 15*60*20);
	}
	
	private static void checkTown(){
		Console.sendMessage("[Tasks] CheckTown has been started!");
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
	
	private static void respawnNpcs(){
		Console.sendMessage("[Tasks] RespawnNPCs has been started!");
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable(){
			public void run(){
				NPCs.despawnAll();
				Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
					public void run(){
						Console.sendMessage("[NPC] All NPCs have been removed and spawned back in");
						NPCs.spawnAll();
					}
				}, 10);
			}
		}, 0, 5*60*20);
	}
	
	private static void updateExp(){
		Console.sendMessage("[Tasks] UpdateExp has been started!");
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable(){
			public void run(){
		        try {
		        	Main.openConnection();
		        	for (Player player : Bukkit.getOnlinePlayers()){
		        		if (player.getGameMode() == GameMode.ADVENTURE){
			        		Console.sendMessage("[MobExp] Updating XP in database for player " + player.getName());
			        		
			        		PreparedStatement sql2 = Main.connection.prepareStatement("SELECT * FROM `exp` WHERE uuid=?;");
			    			sql2.setString(1, player.getUniqueId().toString());
			    			ResultSet resultSet = sql2.executeQuery();
			    			boolean containsPlayer = resultSet.next();
			    			
			    			sql2.close();
			    			resultSet.close();
				        	
				        	if (containsPlayer){
				        		int xp = Exp.get(player);
				        		PreparedStatement updatexp = Main.connection.prepareStatement("UPDATE `exp` SET count=? WHERE uuid=?;");
				        		updatexp.setInt(1, xp + 1);
				        		updatexp.setString(2, player.getUniqueId().toString());
				        		updatexp.executeUpdate();
				        		
				        		PreparedStatement name = Main.connection.prepareStatement("UPDATE `exp` SET name=? where uuid=?;");
				        		name.setString(1, player.getName());
				        		name.setString(2, player.getUniqueId().toString());
				        		name.executeUpdate();
				        		
				        		name.close();
				        		updatexp.close();
				        	} else {
				        		PreparedStatement newplayer = Main.connection.prepareStatement("INSERT INTO `exp` values(?, 0, ?);");
				        		newplayer.setString(1, player.getUniqueId().toString());
				        		newplayer.setString(2, player.getName());
				        		newplayer.execute();
				        		newplayer.close();
				        	}
		        		}
		        	}
		        } catch (Exception e){
		        	e.printStackTrace();
		        } finally {
		        	Main.closeConnection();
		        }
			}
		}, 5*20, 1*60*20);
	}

}
