package com.robinmc.ublisk.listeners;

import org.bukkit.GameMode;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.NPC;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import com.robinmc.ublisk.iconmenus.weaponmerchant.WeaponMerchant;

public class PlayerInteractEntity implements Listener {
	
	@EventHandler
	public void onPlayerInteractEntityEvent(PlayerInteractEntityEvent event) {
		Entity entity = event.getRightClicked();
		Player player = event.getPlayer();
		if (entity instanceof NPC){
			String name = entity.getName();
			WeaponMerchant.open(name, player);
			event.setCancelled(true);
		} else if (entity instanceof ArmorStand && !(player.getGameMode() == GameMode.CREATIVE)){
			event.setCancelled(true);
		}

	}

}
