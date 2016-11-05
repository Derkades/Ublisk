package com.sethbling.blinghomingarrows;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.util.Vector;

public class HomingArrowsListener implements Listener {

	@EventHandler
	public void eventArrowFired(EntityShootBowEvent e){
		if (((e.getEntity() instanceof LivingEntity)) && ((e.getProjectile() instanceof Arrow))){
			LivingEntity player = e.getEntity();
			
			double minAngle = 6.283185307179586D;
			
			Entity minEntity = null;
			for (Entity entity : player.getNearbyEntities(64.0D, 64.0D, 64.0D)) {
				if ((player.hasLineOfSight(entity)) && ((entity instanceof LivingEntity)) && (!entity.isDead())){
					Vector toTarget = entity.getLocation().toVector().clone().subtract(player.getLocation().toVector());
					
					double angle = e.getProjectile().getVelocity().angle(toTarget);
					
					if (angle < minAngle){
						minAngle = angle;
						minEntity = entity;
					}
				}
			}
			
			if (minEntity != null)
				new HomingTask((Arrow) e.getProjectile(), (LivingEntity) minEntity);
		}
	}
	
}