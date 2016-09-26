package com.robinmc.ublisk.listeners.player;

import static org.bukkit.ChatColor.DARK_GRAY;
import static org.bukkit.ChatColor.GRAY;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.robinmc.ublisk.HashMaps;
import com.robinmc.ublisk.chat.Trigger;
import com.robinmc.ublisk.enums.Tracker;
import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.variable.Message;

import net.md_5.bungee.api.ChatColor;

public class AsyncPlayerChat implements Listener {
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent event){		
		
		if (event.isCancelled()){
			return;
		}
		
		UPlayer player = UPlayer.get(event.getPlayer());
		
		if (HashMaps.isMuted.get(player.getUniqueId())){
			player.sendMessage(Message.CANT_CHAT_MUTED.get());
			event.setCancelled(true);
			return;
		}
		
		ChatColor chatColor = ChatColor.WHITE;
		if (HashMaps.isSoftMuted.get(player.getUniqueId())){
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
		
		Tracker.CHAT_MESSAGES.add(player.getPlayer());
		
	}

}
