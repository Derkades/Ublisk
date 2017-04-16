package com.robinmc.ublisk.modules;

import static org.bukkit.ChatColor.DARK_GRAY;
import static org.bukkit.ChatColor.GRAY;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.robinmc.ublisk.HashMaps;
import com.robinmc.ublisk.utils.UPlayer;

import net.md_5.bungee.api.ChatColor;

public class Groups extends UModule {
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onChat(AsyncPlayerChatEvent event){
		UPlayer player = new UPlayer(event);
		
		ChatColor chatColor = ChatColor.WHITE;
		if (HashMaps.IS_SOFT_MUTED.get(player.getUniqueId()))
			chatColor = ChatColor.GRAY;
		
		int level = player.getLevel();
		String prefix = player.getGroup().getPrefix();
		String format = DARK_GRAY + "[" + GRAY + level + DARK_GRAY + "] " + prefix + " %s" + DARK_GRAY + ": " + chatColor + "%s";
		event.setFormat(format);
	}

}
