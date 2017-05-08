package com.robinmc.ublisk.listeners;

import java.util.UUID;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.robinmc.ublisk.database.PlayerInfo;

public class PlayerMove implements Listener {
	
	@EventHandler
	public void onMove(PlayerMoveEvent event){
		if (!event.getFrom().getBlock().equals(event.getTo().getBlock())){
			UUID uuid = event.getPlayer().getUniqueId();
			PlayerInfo.BLOCKS_WALKED.put(uuid, PlayerInfo.BLOCKS_WALKED.get(uuid) + 1);
		}
	}

}
