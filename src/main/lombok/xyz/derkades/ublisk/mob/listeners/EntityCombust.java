package xyz.derkades.ublisk.mob.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;

public class EntityCombust implements Listener {
	
	@EventHandler(ignoreCancelled = true)
	public void onBurn(EntityCombustEvent event){
		event.setCancelled(true);
	}

}
