package com.robinmc.ublisk;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.inventivetalent.bossbar.BossBar;
import org.inventivetalent.bossbar.BossBarAPI;

import com.robinmc.ublisk.utils.Config;
import com.robinmc.ublisk.utils.Console;
import com.robinmc.ublisk.utils.EntityUtils;
import com.robinmc.ublisk.utils.Exp;
import com.robinmc.ublisk.utils.Friends;
import com.robinmc.ublisk.utils.Setting;
import com.robinmc.ublisk.utils.Time;
import com.robinmc.ublisk.utils.UUIDUtils;

import net.md_5.bungee.api.chat.TextComponent;

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
		checkShield();
		checkDoubleExp();
		updateDoubleExpBossBar();
		updateBackpackName();
		friendsHealthBar();
	}
	
	private static void fastNight(){
		Console.sendMessage("[Tasks] FastNight has been started!");
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable(){
			public void run(){
				if (!Time.isDay()){
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
		}, 5*20, 5*20);
	}
	
	private static void clearWeather(){
		Console.sendMessage("[Tasks] ClearWeather has been started!");
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable(){
			public void run(){
				World world = Var.world();
				world.setThundering(false);
				world.setStorm(false);
			}
		}, 60*20, 5*60*20);
	}
	
	private static void randomTip(){
		Console.sendMessage("[Tasks] RandomTips has been started!");
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable(){
			public void run(){
				for (Player player : Bukkit.getOnlinePlayers()){
					player.sendMessage(Tip.getRandom());
				}
			}
		}, 20*20, 5*60*20);
	}
	
	private static void removeMobs(){
		Console.sendMessage("[Tasks] RemoveMobs has been started!");
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable(){
			public void run(){
				Bukkit.broadcastMessage(CMessage.removeMobsWarning(30));
				Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
					public void run(){
						Bukkit.broadcastMessage(CMessage.removeMobsWarning(5));
						Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
							public void run(){
								EntityUtils.removeMobs();
								Bukkit.broadcastMessage(Message.ENTITIES_REMOVED.get());
							}
						}, 5*20);
					}
				}, 25*20);
			}
		}, 5*60*20, 15*60*20);
	}
	
	private static void checkTown(){
		Console.sendMessage("[Tasks] CheckTown has been started!");
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
						Console.sendMessage("[NPC] All NPCs have been respawned!");
						NPCs.spawnAll();
					}
				}, 10);
			}
		}, 5*60*20, 5*60*20);
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
	
	private static void checkShield(){
		Console.sendMessage("[Tasks] CheckShield has been started!");
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable(){
			public void run(){
				for (Player player : Bukkit.getOnlinePlayers()){
					PlayerInventory inv = player.getInventory();
					if (inv.getItemInOffHand().getType() == Material.SHIELD && !(Classes.getClass(player) == Classes.PALADIN)){
						player.sendMessage(Message.CLASS_WRONG_WEAPON.get());
						player.setHealth(0.5);
					}
				}
			}
		}, 5*20, 5*20);
	}
	
	private static void checkDoubleExp(){
		Console.sendMessage("[Tasks] CheckDoubleExp has been started!");
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable(){
			public void run(){
				try {
					Main.openConnection();
					PreparedStatement sql = Main.connection.prepareStatement("SELECT state FROM `bonus` WHERE type='doublexp_1';");
					ResultSet rs = sql.executeQuery();
					rs.next();
	    			boolean doublexp = rs.getBoolean(1);
	    			sql.close();
	    			if (doublexp && !(HashMaps.doublexp.get("hi"))){ //If doublexp is true in database and not yet active
	    				HashMaps.doublexp.put("hi", true); //Enable double xp. The task below will take care of the rest
	    			}
				} catch (Exception e){
					e.printStackTrace();
				} finally {
					Main.closeConnection();
				}
			}
		}, 10*20, 10*20);
	}
	
	private static void updateDoubleExpBossBar(){
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable(){
			public void run(){
				try {
					if (HashMaps.doublexp.get("hi")){
						int left = HashMaps.doublexptime.get("hi");
						for (final Player player : Bukkit.getOnlinePlayers()){
							//double n = ((left * (10 / 6)) / 100); //Converts a range of 0-60 to 0-1
							//Console.sendMessage(left + "");
							//Console.sendMessage((left * (10 / 6)) / 100 + "");
							//BigDecimal bd = new BigDecimal(Double.toString((left * (10 / 6)) / 100));
					        //bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
							//float progress = bd.floatValue();
							//float progress = (float) n;
							//Console.sendMessage(progress + "");
							
							//As you can see from the mess above converting a 0-60 double to 0.0-1.0 float was too hard for me. I left this here in case I want to do this properly later. Doin' it the messy way, sorry fellow coders :(
							float p = 1.0f;
							if (left > 54){
								p = 1.0f;
							} else if (left > 48){
								p = 0.9f;
							} else if (left > 42){
								p = 0.8f;
							} else if (left > 36){
								p = 0.7f;
							} else if (left > 30){
								p = 0.6f;
							} else if (left > 24){
								p = 0.5f;
							} else if (left > 18){
								p = 0.4f;
							} else if (left > 12){
								p = 0.3f;
							} else if (left > 6){
								p = 0.2f;
							} else if (left > 1){
								p = 0.1f;
							} else {
								p = 0.0f;
							}
							final BossBar bossBar = BossBarAPI.addBar(player, new TextComponent("Double XP for 1 minute"), BossBarAPI.Color.GREEN, BossBarAPI.Style.PROGRESS, p, 20, 40);
							Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
								public void run(){
									bossBar.removePlayer(player);
								}
							}, 1*20);
						}
						int left2 = left - 1;
						if (left2 == 0){
							left2 = Var.doubleExpTime();
							HashMaps.doublexp.put("hi", false);
							//Now we need to set it to false in database as well
							try {
								Main.openConnection();
								PreparedStatement sql = Main.connection.prepareStatement("UPDATE bonus SET state=false WHERE type='doublexp_1'");
								sql.executeUpdate();
								sql.close();
							} catch (Exception e){
								e.printStackTrace();
							} finally {
								Main.closeConnection();
							}
						}
						HashMaps.doublexptime.put("hi", left2);
					}
				} catch (NullPointerException e){
					HashMaps.doublexp.put("hi", false);
				}
			}
		}, 1*20, 1*20);
	}
	
	public static void updateBackpackName(){
		Console.sendMessage("[Tasks] UpdateBackpackName has been started!");
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable(){
			public void run(){
				for (Player player : Bukkit.getOnlinePlayers()){
					PlayerInventory inv = player.getInventory();
					if (inv.getItemInMainHand().getType() == Material.END_CRYSTAL){
						inv.remove(Material.END_CRYSTAL);
						ItemStack item = new ItemStack(Material.END_CRYSTAL);
						ItemMeta meta = item.getItemMeta();
						meta.setDisplayName(ChatColor.BLUE + "" + ChatColor.BOLD + "Backpack");
						item.setItemMeta(meta);			
						int slot = inv.getHeldItemSlot();
						inv.setItem(slot, item);
					}
				}
			}
		}, 5*20, 5*20);
	}
	
	public static void friendsHealthBar(){
		Console.sendMessage("[Tasks] FriendsHealthBar has been started!");
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable(){
			public void run(){
				for (final Player player : Bukkit.getOnlinePlayers()){
					if (!Setting.FRIENDS_SHOW_HEALTH.get(player)){
						return;
					}
					
					for (String s : Friends.get(player)){
						OfflinePlayer friend = UUIDUtils.getPlayerFromName(UUIDUtils.getNameFromIdString(s));
						if (friend.isOnline()){
							Player onlineFriend = (Player) friend;
							TextComponent text = new TextComponent(onlineFriend.getName() + "'s health");
							double h = onlineFriend.getHealth();
							float p = 0;
							if (h == 1){
								p = 0.05f;
							} else if (h == 2){
								p = 0.1f;
							} else if (h == 3){
								p = 0.15f;
							} else if (h == 4){
								p = 0.2f;
							} else if (h == 5){
								p = 0.25f;
							} else if (h == 6){
								p = 0.3f;
							} else if (h == 7){
								p = 0.35f;
							} else if (h == 8){
								p = 0.4f;
							} else if (h == 9){
								p = 0.45f;
							} else if (h == 10){
								p = 0.5f;
							} else if (h == 11){
								p = 0.55f;
							} else if (h == 12){
								p = 0.6f;
							} else if (h == 13){
								p = 0.65f;
							} else if (h == 14){
								p = 0.7f;
							} else if (h == 15){
								p = 0.75f;
							} else if (h == 16){
								p = 0.8f;
							} else if (h == 17){
								p = 0.85f;
							} else if (h == 18){
								p = 0.9f;
							} else if (h == 19){
								p = 0.95f;
							} else {
								p = 1.0f;
							}
							
							final BossBar bossBar = BossBarAPI.addBar(
									player, 
									text, 
									BossBarAPI.Color.RED, 
									BossBarAPI.Style.NOTCHED_20,
									p, 
									20, 
									999);
							Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
								public void run(){
									bossBar.removePlayer(player);
								}
							}, 2*20); // Remove after 5 seconds to make room for new bar
						}
					}
				}
			}
		}, 5*20, 2*20);
	}

}
