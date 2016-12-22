package com.robinmc.ublisk.listeners.player;

import static org.bukkit.ChatColor.DARK_GRAY;
import static org.bukkit.ChatColor.GRAY;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.robinmc.ublisk.HashMaps;
import com.robinmc.ublisk.Message;
import com.robinmc.ublisk.NewTracker;
import com.robinmc.ublisk.chat.Trigger;
import com.robinmc.ublisk.utils.UPlayer;

import net.md_5.bungee.api.ChatColor;

public class AsyncPlayerChat implements Listener {
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent event){		
		
		if (event.isCancelled()){
			return;
		}
		
		UPlayer player = UPlayer.get(event.getPlayer());
		
		if (HashMaps.IS_MUTED.get(player.getUniqueId())){
			player.sendMessage(Message.CANT_CHAT_MUTED.get());
			event.setCancelled(true);
			return;
		}
		
		ChatColor chatColor = ChatColor.WHITE;
		if (HashMaps.IS_SOFT_MUTED.get(player.getUniqueId())){
			chatColor = ChatColor.GRAY;
		}
		
		for (Trigger trigger : Trigger.values()){
			if (event.getMessage().equals(trigger.getTrigger())){
				event.setMessage(trigger.getMessage());
			}
		}
		
		int level = player.getLevel();
		String prefix = player.getGroup().getPrefix();
		String format = DARK_GRAY + "[" + GRAY + level + DARK_GRAY + "] " + prefix + " " + player.getName() + DARK_GRAY + ": " + chatColor + event.getMessage();
		event.setFormat(format);
		
		//Tracker.CHAT_MESSAGES.add(player.getPlayer());
		player.tracker(NewTracker.CHAT_MESSAGES);
		
		if (player.isAfk()){
			player.setAfk(false);
		}
		
	}

}
