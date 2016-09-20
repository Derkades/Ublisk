package com.robinmc.ublisk.utils.mob;

import static net.md_5.bungee.api.ChatColor.DARK_AQUA;
import static net.md_5.bungee.api.ChatColor.DARK_GRAY;
import static net.md_5.bungee.api.ChatColor.DARK_GREEN;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;

import com.robinmc.ublisk.utils.java.Random;
import com.robinmc.ublisk.utils.scheduler.Scheduler;
import com.robinmc.ublisk.utils.variable.Var;

class SpawnMob {
	
	static void spawnMobs(){
		for (final Mob mob : Mob.values()){
			double rate = mob.getSpawnRate() * 20;
			Scheduler.runSyncRepeatingTask(5*20, (int) rate, new Runnable(){
				public void run(){
					
					if (Bukkit.getOnlinePlayers().size() == 0){
						return;
					}
					
					if (mob.hasReachedMax()){
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
					
					Location loc = new Location(Var.WORLD, x, 100, z);

					List<Material> materials = Arrays.asList(
							Material.AIR, 
							Material.LONG_GRASS,
							Material.CHORUS_FLOWER,
							Material.YELLOW_FLOWER,
							Material.TORCH,
							Material.SUGAR_CANE_BLOCK);
					
					while(materials.contains(loc.getBlock().getType())){
						loc.setY(loc.getY() - 1);
					}
					
					List<Material> cancel = Arrays.asList(
							Material.FENCE,
							Material.FENCE_GATE,
							Material.COBBLE_WALL,
							Material.WATER,
							Material.STATIONARY_WATER,
							Material.LEAVES,
							Material.LEAVES_2);
					
					//Don't let mobs spawn on top of the blocks above
					if (cancel.contains(loc.getBlock().getType())){
						return; 
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
