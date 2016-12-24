package com.robinmc.ublisk.listeners.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import com.robinmc.ublisk.Message;
import com.robinmc.ublisk.utils.UPlayer;

public class PlayerItemConsume implements Listener {
	
	@EventHandler
	public void eat(PlayerItemConsumeEvent event){
		
		if (event.isCancelled()){
			return;
		}
		
		UPlayer player = UPlayer.get(event.getPlayer());
		player.sendMessage(Message.CANT_EAT);
		event.setCancelled(true);
	}

}
