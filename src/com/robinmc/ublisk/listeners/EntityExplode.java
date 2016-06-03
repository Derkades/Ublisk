package com.robinmc.ublisk.listeners;

import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

import com.robinmc.ublisk.Messages;
import com.robinmc.ublisk.utils.BanUtils;

public class EntityExplode implements Listener {

	@EventHandler
	public void onEntityExplode(EntityExplodeEvent event){
		event.setCancelled(true);
		Entity entity = event.getEntity();
		boolean found = false;
			for (int i = 0; i < 200; i++) {
				List<Entity> entities = entity.getNearbyEntities(i,64,i);
				for (Entity e : entities) {
					if (e.getType().equals(EntityType.PLAYER)) {
						Player player = (Player) e;
						BanUtils.tempBan(player, 30);
						player.kickPlayer(Messages.tntBan());
						found = true;
						break;
					}
				}
			if (found) break;
		}
	}

}
