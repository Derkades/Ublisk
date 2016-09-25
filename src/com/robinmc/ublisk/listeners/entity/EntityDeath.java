package com.robinmc.ublisk.listeners.entity;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;

import com.robinmc.ublisk.enums.Tracker;
import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.exception.MobNotFoundException;
import com.robinmc.ublisk.utils.logging.LogLevel;
import com.robinmc.ublisk.utils.logging.Logger;
import com.robinmc.ublisk.utils.mob.Mob;
import com.robinmc.ublisk.utils.mob.MobDrop;
import com.robinmc.ublisk.utils.variable.Message;

public class EntityDeath implements Listener {
	
	@EventHandler
	public void entityDeath(EntityDeathEvent event){
		LivingEntity entity = event.getEntity();
		
		if (entity.getType() == EntityType.PLAYER){
			return;
		}
		
		event.setDroppedExp(0);
		
		if (entity.getLastDamageCause().getCause() == DamageCause.ENTITY_ATTACK){
			UPlayer player = UPlayer.get(entity.getKiller());
			
			if (!Mob.containsEntityType(entity.getType())){
				return;
			}
			
			player.tracker(Tracker.MOB_KILLS);
			
			try {
				player.giveMobXP(entity);
				
				Mob mob = Mob.getMob(entity);
				
				if (mob.getDrops().length > 0){
					for (MobDrop drop : mob.getDrops()){
						drop.drop(entity.getLocation());
						Logger.log(LogLevel.DEBUG, "Mobs", "Mob drop " + drop.getItemStack().getType());
					}
					
					for (MobDrop drop : mob.getGoldDrop().getMobDrops()){
						drop.drop(entity.getLocation());
						Logger.log(LogLevel.DEBUG, "Mobs", "Mob drop " + drop.getItemStack().getType());
					}
				}
				
			} catch (MobNotFoundException e) {
				player.sendMessage(Message.ERROR_GENERAL.get());
				Location loc = entity.getLocation();
				player.sendMessage("Entity: " + entity.getType());
				player.sendMessage("Name: " + entity.getCustomName());
				player.sendMessage("Location: " + loc.getBlockX() + " " + loc.getBlockY() + " " + loc.getBlockZ());
				e.printStackTrace();
			}
		}
	}

}
