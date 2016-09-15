package com.robinmc.ublisk.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;

import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.enums.Tracker;
import com.robinmc.ublisk.utils.Exp;
import com.robinmc.ublisk.utils.exception.MobNotFoundException;
import com.robinmc.ublisk.utils.exception.UnknownAreaException;
import com.robinmc.ublisk.utils.mob.Mob;
import com.robinmc.ublisk.utils.variable.Message;
import com.robinmc.ublisk.utils.variable.Var;

public class EntityDeath implements Listener {
	
	@EventHandler
	public void entityDeath(EntityDeathEvent event){
		LivingEntity entity = event.getEntity();
		
		if (entity.getType() == EntityType.PLAYER){
			return;
		}
		
		if (entity.getLastDamageCause().getCause() == DamageCause.ENTITY_ATTACK){
			Player player = entity.getKiller();
			if (Mob.containsEntity(entity)){
				Tracker.MOB_KILLS.add(player);
				Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable(){
					public void run(){
						for (Entity entity : Var.WORLD.getEntities()){
							if (entity.getType() == EntityType.EXPERIENCE_ORB){
								entity.remove();
							}
						}
					}
				}, 2);
				
				try {
					Exp.giveMobExp(player, entity);
					Exp.refresh(player);
				} catch (MobNotFoundException | UnknownAreaException e) {
					player.sendMessage(Message.ERROR_GENERAL.get());
					Location loc = entity.getLocation();
					player.sendMessage("Entity: " + entity.getType());
					player.sendMessage("Name: " + entity.getCustomName());
					player.sendMessage("Location: " + loc.getBlockX() + " " + loc.getBlockY() + " " + loc.getBlockZ());
				}
			}
		}
	}

}
