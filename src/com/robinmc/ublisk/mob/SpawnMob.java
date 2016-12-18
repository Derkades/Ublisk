package com.robinmc.ublisk.mob;

import static org.bukkit.ChatColor.DARK_AQUA;
import static org.bukkit.ChatColor.DARK_GRAY;
import static org.bukkit.ChatColor.DARK_GREEN;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;

import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.Var;
import com.robinmc.ublisk.utils.Logger;
import com.robinmc.ublisk.utils.Logger.LogLevel;
import com.robinmc.ublisk.utils.java.Random;

class SpawnMob {
	
	static void spawnMobs(){
		for (final Mob mob : Mob.values()){
			double rate = mob.getSpawnRate() * 20;
			new BukkitRunnable(){
				public void run(){
					for (Radius area : mob.getAreas()){
						if (Bukkit.getOnlinePlayers().size() == 0){
							break;
						}
												
						if (mob.hasReachedMaxSpawning()){
							break;
						}
						
						boolean positiveX = Random.getRandomBoolean();
						boolean positiveZ = Random.getRandomBoolean();
	
						int radius = area.getRadius();
						
						int x = area.getX();
						int z = area.getZ();
						
						int randomX = Random.getRandomInteger(0, radius);
						int randomZ = Random.getRandomInteger(0, radius);
						
						if (positiveX){
							x = x + randomX;
						} else {
							x = x - randomX;
						}
						
						if (positiveZ){
							z = z + randomZ;
						} else {
							z = z - randomZ;
						}
						
						Location loc = new Location(Var.WORLD, x, area.getY(), z);
						
						if (!loc.getChunk().isLoaded()){
							Logger.log(LogLevel.INFO, "Spawning of a " + mob.getName() + " at " + loc.getX() + "," + loc.getY() + "," + loc.getZ() + " has been cancelled, because the chunk is not loaded.");
							continue;
						}

						while(Var.MOB_SPAWNING_AIR_BLOCKS.contains(loc.getBlock().getType())){
							loc.setY(loc.getY() - 1);
						}
						
						//Don't let mobs spawn on top of some blocks
						if (Var.MOB_SPAWNING_CANCEL.contains(loc.getBlock().getType())){
							continue; 
						}
						
						loc.setY(loc.getY() + 1);
						
						if (!Var.MOB_SPAWNING_AIR_BLOCKS.contains(loc.getBlock().getType()))
								return;
						
						String name = DARK_AQUA + mob.getName() + DARK_GRAY + " [" + DARK_GREEN + mob.getLevel() + DARK_GRAY + "]";
						
						LivingEntity entity = (LivingEntity) Var.WORLD.spawnEntity(loc, mob.getEntityType());
						
						entity.setCustomName(name);
						entity.setCustomNameVisible(true);
						double health = mob.getHealth();
						entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(health);
						entity.setHealth(health);
						entity.setRemoveWhenFarAway(true);
						Location tp = entity.getLocation();
						tp.setPitch(Random.getRandomFloat(0, 360));
						
						entity.teleport(tp);
						
						MobCode code = mob.getMobType().getCode();
						if (code != null)
							code.mobCode(entity);
					}
				}
			}.runTaskTimer(Main.getInstance(), 2*20, (int) rate);
		}	
	}

}
