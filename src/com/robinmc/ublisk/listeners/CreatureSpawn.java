package com.robinmc.ublisk.listeners;

import static net.md_5.bungee.api.ChatColor.DARK_AQUA;
import static net.md_5.bungee.api.ChatColor.DARK_GRAY;
import static net.md_5.bungee.api.ChatColor.DARK_GREEN;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import com.robinmc.ublisk.utils.exception.MobInfoMissingException;
import com.robinmc.ublisk.utils.exception.MobNotFoundException;
import com.robinmc.ublisk.utils.exception.UnknownAreaException;
import com.robinmc.ublisk.utils.logging.LogLevel;
import com.robinmc.ublisk.utils.logging.Logger;
import com.robinmc.ublisk.utils.mob.Mob;
import com.robinmc.ublisk.utils.mob.MobArea;
import com.robinmc.ublisk.utils.mob.MobInfo;

public class CreatureSpawn implements Listener {
	
	@EventHandler
	public void onSpawn(CreatureSpawnEvent event){
		LivingEntity entity = event.getEntity();
		
		if (entity instanceof ArmorStand){
			event.setCancelled(false);
			return;
		}
		
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
	}

}
