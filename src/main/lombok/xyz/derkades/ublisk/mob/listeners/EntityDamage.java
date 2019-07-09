package xyz.derkades.ublisk.mob.listeners;

import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import xyz.derkades.ublisk.mob.Mobs;
import xyz.derkades.ublisk.utils.Logger;
import xyz.derkades.ublisk.utils.Ublisk;
import xyz.derkades.ublisk.utils.Logger.LogLevel;

public class EntityDamage implements Listener {
	
	@EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
	public void onDamage(EntityDamageEvent event){
		Logger.log(LogLevel.DEBUG, "test");
		Entity entity = event.getEntity();
		if (Mobs.SPAWNED_MOBS.containsKey(entity.getUniqueId())){
			Logger.log(LogLevel.DEBUG, "test2");
			Ublisk.spawnParticle(Particle.BLOCK_DUST, entity.getLocation(), 255, 0, 0, 5);
		}
	}

}
