package com.robinmc.ublisk.listeners.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.robinmc.ublisk.Message;
import com.robinmc.ublisk.utils.UPlayer;

public class PlayerQuit implements Listener {
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event){
		UPlayer player = UPlayer.get(event);
		event.setQuitMessage(Message.Complicated.JoinQuit.quit(player.getName()));
		player.refreshLastSeenDate();
	}

}
