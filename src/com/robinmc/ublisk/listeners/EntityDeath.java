package com.robinmc.ublisk.listeners;

import org.bukkit.craftbukkit.v1_9_R1.entity.CraftZombie;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;

import com.robinmc.ublisk.Mob;
import com.robinmc.ublisk.utils.Exp;

public class EntityDeath implements Listener {
	
	@EventHandler
	public void entityDeath(EntityDeathEvent event){		
		LivingEntity entity = event.getEntity();
		if (entity.getLastDamageCause().getCause() == DamageCause.ENTITY_ATTACK){
			Player player = entity.getKiller();
			if (entity instanceof Chicken){
				Exp.giveMobExp(player, Mob.CHICKEN);
			} else if (entity instanceof Zombie){
				CraftZombie zombie = (CraftZombie) entity;
				if (zombie.getHandle().isVillager()){
					if (zombie.getName().equals("Zombified Merchant")){
						Exp.giveMobExp(player, Mob.ZOMBIFIED_MERCHANT);
					} else {
						return;
					}
				} else {
					return;
				}
			} else if (entity instanceof Sheep){
				Exp.giveMobExp(player, Mob.SHEEP);
			} else {
				return;
			}
		}
	}

}
