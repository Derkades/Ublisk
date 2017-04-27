package com.robinmc.ublisk.mob.v2;

import static org.bukkit.ChatColor.DARK_AQUA;
import static org.bukkit.ChatColor.DARK_GRAY;
import static org.bukkit.ChatColor.DARK_GREEN;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;

import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.Var;
import com.robinmc.ublisk.mob.MobCode;
import com.robinmc.ublisk.mob.Radius;
import com.robinmc.ublisk.utils.Logger;
import com.robinmc.ublisk.utils.Logger.LogLevel;
import com.robinmc.ublisk.utils.Ublisk;
import com.robinmc.ublisk.utils.java.Random;

public class MobSpawn {

	/**
	 * List of materials that are on top of a solid block and which mobs should spawn inside of
	 */
	private static final List<Material> MOB_SPAWNING_AIR_BLOCKS = Arrays.asList(
			Material.AIR, 
			Material.LONG_GRASS,
			Material.CHORUS_FLOWER,
			Material.YELLOW_FLOWER,
			Material.TORCH,
			Material.SUGAR_CANE_BLOCK
			);
	
	/**
	 * Do not spawn mobs on top of these blocks
	 */
	private static final List<Material> MOB_SPAWNING_CANCEL = Arrays.asList(
			Material.FENCE,
			Material.FENCE_GATE,
			Material.COBBLE_WALL,
			Material.WATER,
			Material.STATIONARY_WATER,
			Material.LEAVES,
			Material.LEAVES_2
			);
	
	private static boolean MOB_SPAWNING_ACTIVE = false;
	
	public static void startMobSpawning(){
		if (MOB_SPAWNING_ACTIVE){
			throw new UnsupportedOperationException("Mob spawning is already active");
		} else {
			MOB_SPAWNING_ACTIVE = true;
		}
		
		for (final Mob mob : Mobs.MOB_LIST){
			double rate = mob.getSpawnRate() * 20;
			new BukkitRunnable(){
				public void run(){
					if (Bukkit.getOnlinePlayers().size() == 0 || mob.hasReachedSpawnLimit()){
						return;
					}

					Radius area = mob.getArea();
						
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
						return;
					}

					while(MOB_SPAWNING_AIR_BLOCKS.contains(loc.getBlock().getType())){
						loc.setY(loc.getY() - 1);
					}
						
					//Don't let mobs spawn on top of some blocks
					if (MOB_SPAWNING_CANCEL.contains(loc.getBlock().getType())){
						return; 
					}
						
					loc.setY(loc.getY() + 1);
						
					if (!MOB_SPAWNING_AIR_BLOCKS.contains(loc.getBlock().getType()))
						return;
						
					String name = DARK_AQUA + mob.getName() + DARK_GRAY + " [" + DARK_GREEN + mob.getLevel() + DARK_GRAY + "]";
						
					LivingEntity entity = (LivingEntity) Var.WORLD.spawnEntity(loc, mob.getEntityType());
					
					// Apply custom data
					entity.setCustomName(name);
					entity.setCustomNameVisible(true);
					double health = mob.getHealth();
					entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(health);
					entity.setHealth(health);
					entity.setRemoveWhenFarAway(true);
					
					// Look in a random direction
					Location tp = entity.getLocation();
					tp.setYaw(Random.getRandomInteger(0, 360));
					entity.teleport(tp);
					
					Ublisk.spawnParticle(Particle.EXPLOSION_NORMAL, tp, 20, 0, 0, 0, 0.1);
					
					// Execute mob code, if any
					MobCode code = mob.getMobCode();
					if (code != null)
						code.mobCode(entity);
					
					Mobs.SPAWNED_MOBS.put(entity.getUniqueId(), mob);
				}
			}.runTaskTimer(Main.getInstance(), 2*20, (long) rate);
		}	
	}

}
