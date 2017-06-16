package xyz.derkades.ublisk.modules;

import org.apache.commons.lang3.ArrayUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import xyz.derkades.ublisk.HashMaps;
import xyz.derkades.ublisk.utils.UPlayer;
import xyz.derkades.ublisk.utils.Ublisk;

public class FormatChat extends UModule {
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onChat(AsyncPlayerChatEvent event){
		event.setCancelled(true); // Don't send chat message, we'll send a custom message
		
		UPlayer player = new UPlayer(event);
		
		ChatColor chatColor = ChatColor.WHITE;
		if (HashMaps.IS_SOFT_MUTED.get(player.getUniqueId()))
			chatColor = ChatColor.GRAY;
		
		int level = player.getLevel();
		
		BaseComponent[] message = new ComponentBuilder("")
				.append("[").reset().color(ChatColor.DARK_GRAY)
				.append(level + "").reset().color(ChatColor.GRAY)
				.append("] ").reset().color(ChatColor.DARK_GRAY)
				.append(player.getGroup().getName()).color(player.getGroup().getPrefixColor()).bold(player.getGroup().nameBold())
				.append(":").reset().color(ChatColor.DARK_GRAY)
				.append(" ")
				.create();
		
		message = ArrayUtils.addAll(message, player.getDisplayName(player.getGroup().getNameColor(), player.getGroup().nameBold()));

		message = ArrayUtils.addAll(message, new ComponentBuilder("")
				.append(": ").reset().color(ChatColor.DARK_GRAY).bold(true)
				.append(event.getMessage()).reset().color(chatColor)
				.create());
		
		Ublisk.getServer().spigot().broadcast(message);		
	}

}
