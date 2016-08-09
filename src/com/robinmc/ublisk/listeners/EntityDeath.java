package com.robinmc.ublisk.listeners;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;

import com.robinmc.ublisk.utils.Exp;
import com.robinmc.ublisk.utils.enums.Tracker;
import com.robinmc.ublisk.utils.exception.MobNotFoundException;
import com.robinmc.ublisk.utils.exception.UnknownAreaException;
import com.robinmc.ublisk.utils.mob.Mob;
import com.robinmc.ublisk.utils.variable.Message;

public class EntityDeath implements Listener {
	
	@EventHandler
	public void entityDeath(EntityDeathEvent event){
		LivingEntity entity = event.getEntity();
		if (entity.getLastDamageCause().getCause() == DamageCause.ENTITY_ATTACK){
			Player player = entity.getKiller();
			if (Mob.containsEntity(entity)){
				Tracker.MOB_KILLS.add(player);
				try {
					Exp.giveMobExp(player, entity);
				} catch (MobNotFoundException | UnknownAreaException e) {
					player.sendMessage(Message.ERROR_GENERAL.get());
				}
			}
		}
	}

}
