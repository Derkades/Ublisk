package com.robinmc.ublisk.modules;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.robinmc.ublisk.HashMaps;
import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.Ublisk;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;

public class Groups extends UModule {
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onChat(AsyncPlayerChatEvent event){
		event.setCancelled(true); // Don't send chat message, we'll send a custom message
		
		UPlayer player = new UPlayer(event);
		
		ChatColor chatColor = ChatColor.WHITE;
		if (HashMaps.IS_SOFT_MUTED.get(player.getUniqueId()))
			chatColor = ChatColor.GRAY;
		
		int level = player.getLevel();
		//String prefix = player.getGroup().getPrefix();
		//String message = DARK_GRAY + "[" + GRAY + level + DARK_GRAY + "] " + prefix + " " + player.getName() + DARK_GRAY + ": " + chatColor + event.getMessage();
		
		BaseComponent[] message = new ComponentBuilder("")
				.append("[").reset().color(ChatColor.DARK_GRAY)
				.append(level + "").reset().color(ChatColor.GRAY)
				.append("] ").reset().color(ChatColor.DARK_GRAY)
				.append("Prefix")
				.append(": ").reset().color(ChatColor.DARK_GRAY)
				.append(" ")
				.append(player.getName()).reset().event(new HoverEvent(
						HoverEvent.Action.SHOW_TEXT,
						new ComponentBuilder("XP: " + player.getXP()).color(ChatColor.AQUA)
						.append("\n")
						.append("Guild: " + player.getGuildName()).color(ChatColor.AQUA)
						.append("\n\n")
						.append("Click to open statistics").color(ChatColor.GRAY).italic(true)
						.create()
						)).event(new ClickEvent(
								ClickEvent.Action.OPEN_URL, player.getStatsURL()
							))
				.append(": ").reset().color(ChatColor.DARK_GRAY).bold(true)
				.append(event.getMessage()).reset().color(chatColor)
				.create();
		
		Ublisk.getServer().spigot().broadcast(message);		
	}

}
