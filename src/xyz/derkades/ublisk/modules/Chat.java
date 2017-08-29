package xyz.derkades.ublisk.modules;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.ArrayUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import xyz.derkades.ublisk.Message;
import xyz.derkades.ublisk.chat.Trigger;
import xyz.derkades.ublisk.database.PlayerInfo;
import xyz.derkades.ublisk.utils.Logger;
import xyz.derkades.ublisk.utils.UPlayer;
import xyz.derkades.ublisk.utils.Ublisk;
import xyz.derkades.ublisk.utils.Logger.LogLevel;

public class Chat extends UModule {
	
	// TODO Use arraylist instead
	public static final Map<UUID, Boolean> IS_MUTED = new HashMap<>();
	public static final Map<UUID, Boolean> IS_SOFT_MUTED = new HashMap<>();
	
	@Override
	public void onEnable(){
		for (UPlayer player : Ublisk.getOnlinePlayers()){
			IS_MUTED.put(player.getUniqueId(), false);
			IS_SOFT_MUTED.put(player.getUniqueId(), false);
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onJoin(PlayerJoinEvent event){
		UUID uuid = event.getPlayer().getUniqueId();
		
		if (!IS_MUTED.containsKey(uuid)){
			IS_MUTED.put(uuid, false);
		}
		
		if (!IS_SOFT_MUTED.containsKey(uuid)){
			IS_SOFT_MUTED.put(uuid, false);
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onChat(AsyncPlayerChatEvent event){
		event.setCancelled(true); // Don't send chat message, we'll send a custom message
		
		UPlayer player = new UPlayer(event);
		
		if (Chat.IS_MUTED.get(player.getUniqueId())){
			player.sendMessage(Message.CANT_CHAT_MUTED);
			event.setCancelled(true);
			return;
		}
		
		for (Trigger trigger : Trigger.values()){
			if (event.getMessage().equals(trigger.getTrigger())){
				event.setMessage(trigger.getMessage());
			}
		}
		
		ChatColor chatColor = ChatColor.WHITE;
		if (IS_SOFT_MUTED.get(player.getUniqueId()))
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

	@EventHandler(priority = EventPriority.MONITOR)
	public void log(AsyncPlayerChatEvent event){
		UPlayer player = new UPlayer(event);

		player.tracker(PlayerInfo.CHAT_MESSAGES);
		
		Logger.log(LogLevel.CHAT, player.getName(), event.getMessage());
	}

}
