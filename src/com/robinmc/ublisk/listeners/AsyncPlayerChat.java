package com.robinmc.ublisk.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.robinmc.ublisk.HashMaps;
import com.robinmc.ublisk.Message;
import com.robinmc.ublisk.chat.Trigger;
import com.robinmc.ublisk.database.PlayerInfo;
import com.robinmc.ublisk.utils.Logger;
import com.robinmc.ublisk.utils.Logger.LogLevel;
import com.robinmc.ublisk.utils.UPlayer;

public class AsyncPlayerChat implements Listener {
	
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onChat(AsyncPlayerChatEvent event){		
		UPlayer player = new UPlayer(event);
		
		if (HashMaps.IS_MUTED.get(player.getUniqueId())){
			player.sendMessage(Message.CANT_CHAT_MUTED);
			event.setCancelled(true);
			return;
		}
		
		for (Trigger trigger : Trigger.values()){
			if (event.getMessage().equals(trigger.getTrigger())){
				event.setMessage(trigger.getMessage());
			}
		}
		
		player.tracker(PlayerInfo.CHAT_MESSAGES);		
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = false)
	public void logChat(AsyncPlayerChatEvent event){
		String playerName = event.getPlayer().getName();
		String message = event.getMessage();
		Logger.log(LogLevel.CHAT, playerName, message);
	}

}
