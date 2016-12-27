package com.robinmc.ublisk.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.robinmc.ublisk.HashMaps;
import com.robinmc.ublisk.utils.UPlayer;

public class PlayerMove implements Listener {

	@EventHandler
	public void onMove(PlayerMoveEvent event){
		UPlayer player = new UPlayer(event);
		
		player.resetAfkTimer();
		
		if (player.isAfk())
			player.setAfk(false);
		
		HashMaps.AFK_MINUTES.put(player.getUniqueId(), 0);
	}

}
