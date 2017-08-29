package xyz.derkades.ublisk.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import xyz.derkades.ublisk.Message;
import xyz.derkades.ublisk.chat.Trigger;
import xyz.derkades.ublisk.database.PlayerInfo;
import xyz.derkades.ublisk.modules.Chat;
import xyz.derkades.ublisk.utils.Logger;
import xyz.derkades.ublisk.utils.Logger.LogLevel;
import xyz.derkades.ublisk.utils.UPlayer;

public class AsyncPlayerChat implements Listener {
	
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onChat(AsyncPlayerChatEvent event){		
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
		
		player.tracker(PlayerInfo.CHAT_MESSAGES);		
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = false)
	public void logChat(AsyncPlayerChatEvent event){
		String playerName = event.getPlayer().getName();
		String message = event.getMessage();
		Logger.log(LogLevel.CHAT, playerName, message);
	}

}
