package com.robinmc.ublisk.listeners;

import org.bukkit.craftbukkit.v1_9_R1.entity.CraftZombie;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import com.robinmc.ublisk.Mob;
import com.robinmc.ublisk.MobExp;
import com.robinmc.ublisk.utils.Console;

public class EntityDeath implements Listener {
	
	@EventHandler
	public void entityDeath(EntityDeathEvent event){		
		LivingEntity entity = event.getEntity();
		Player player = entity.getKiller();
		if (entity instanceof Chicken){
			MobExp.giveExp(player, Mob.CHICKEN);
		} else if (entity instanceof Zombie){
			CraftZombie zombie = (CraftZombie) entity;
			if (zombie.getHandle().isVillager()){
				if (zombie.getName().equals("Zombified Merchant")){
					MobExp.giveExp(player, Mob.ZOMBIFIED_MERCHANT);
				} else {
					return;
				}
			} else {
				return;
			}
		} else {
			return;
		}
	}

}
