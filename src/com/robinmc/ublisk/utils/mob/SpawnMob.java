package com.robinmc.ublisk.utils.mob;

import static net.md_5.bungee.api.ChatColor.DARK_AQUA;
import static net.md_5.bungee.api.ChatColor.DARK_GRAY;
import static net.md_5.bungee.api.ChatColor.DARK_GREEN;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;

import com.robinmc.ublisk.utils.java.Random;
import com.robinmc.ublisk.utils.logging.LogLevel;
import com.robinmc.ublisk.utils.logging.Logger;
import com.robinmc.ublisk.utils.scheduler.Scheduler;
import com.robinmc.ublisk.utils.variable.Var;

class SpawnMob {
	
	static void spawnMobs(){
		for (final Mob mob : Mob.values()){
			double rate = mob.getSpawnRate() * 20;
			Scheduler.runSyncRepeatingTask(5*20, (int) rate, new Runnable(){
				public void run(){
					
					if (mob.hasReachedMax()){
						Logger.log(LogLevel.DEBUG, "Mob " + mob.getName() + " has reached maximum spawning");
						return;
					}
					
					boolean positiveX = Random.getRandomBoolean();
					boolean positiveZ = Random.getRandomBoolean();

					int radius = mob.getRadius();
					
					int x = mob.getX();
					int z = mob.getZ();
					
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
					
					Location loc = new Location(Var.WORLD, x, 255, z);
					
					while(loc.getBlock().getType() == Material.AIR){
						loc.setY(loc.getY() - 1);
					}
					
					loc.setY(loc.getY() + 1);
					
					String name = DARK_AQUA + mob.getName() + DARK_GRAY + " [" + DARK_GREEN + mob.getLevel() + DARK_GRAY + "]";
					
					LivingEntity entity = (LivingEntity) Var.WORLD.spawnEntity(loc, mob.getEntityType());
					
					entity.setCustomName(name);
					entity.setCustomNameVisible(true);
					double health = mob.getHealth();
					entity.setMaxHealth(health);
					entity.setHealth(health);
				}
			});
		}	
	}

}
