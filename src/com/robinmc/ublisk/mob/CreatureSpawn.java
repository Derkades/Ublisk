package com.robinmc.ublisk.mob;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class CreatureSpawn implements Listener {
	
	@EventHandler
	public void onSpawn(CreatureSpawnEvent event){
		LivingEntity entity = event.getEntity();
		
		if (entity instanceof ArmorStand ||
				entity instanceof Villager ||
				entity instanceof Item){
			event.setCancelled(false);
			return;
		}
		
		/*
		if (!Mob.containsEntity(entity)){
			event.setCancelled(true);
			return;
		}
		
		try {
			if (Mob.belongsInArea(entity)){
				MobArea area = Mob.getArea(entity);
				MobInfo info = MobArea.getMobInfo(area, entity.getType());
				
				String name = DARK_AQUA + info.getName() + DARK_GRAY + " [" + DARK_GREEN + info.getLevel() + DARK_GRAY + "]";
				entity.setCustomName(name);
				entity.setCustomNameVisible(true);
				double health = info.getHealth();
				entity.setMaxHealth(health);
				entity.setHealth(health);
			} else {
				Logger.log(LogLevel.INFO, "MobSpawning", "Cancelled the spawning of a " + entity.getName() + " at " + entity.getLocation().getBlockX() + "," + entity.getLocation().getBlockZ());
				event.setCancelled(true);
			}
		} catch (MobInfoMissingException e) {
			Logger.log(LogLevel.SEVERE, "MobSpawning", "Mob information missing for " + entity.getName());
		} catch (UnknownAreaException e){
			Location loc = entity.getLocation();
			int x = loc.getBlockX();
			int y = loc.getBlockY();
			int z = loc.getBlockZ();
			Logger.log(LogLevel.WARNING, "MobSpawning", "Mob spawned outside of an area - " + entity.getName() + " at " + x + "," + y + "," + z);
		} catch (MobNotFoundException e) {
			e.printStackTrace();
		}
		*/
	}

}
