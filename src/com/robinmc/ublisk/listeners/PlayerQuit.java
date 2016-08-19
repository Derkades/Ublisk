package com.robinmc.ublisk.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.robinmc.ublisk.enums.Tracker;
import com.robinmc.ublisk.utils.variable.CMessage;

public class PlayerQuit implements Listener {
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event){
		Player player = event.getPlayer();
		event.setQuitMessage(CMessage.quit(player.getName()));
		for (Tracker tracker : Tracker.values()){
			Tracker.syncWithDatabase(player, tracker);
		}
	}

}
