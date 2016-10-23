package com.robinmc.ublisk.listeners.player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;

import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.Message;
import com.robinmc.ublisk.utils.logging.LogLevel;
import com.robinmc.ublisk.utils.logging.Logger;

public class PlayerResourcePackStatus implements Listener {
	
	@EventHandler
	public void pack(PlayerResourcePackStatusEvent event){
		final Player player = event.getPlayer();
		String pn = player.getName();
		if (event.getStatus() == PlayerResourcePackStatusEvent.Status.DECLINED){
			Bukkit.getScheduler().runTask(Main.getInstance(), new Runnable() {
				public void run() {
					player.kickPlayer(Message.PACK_DECLINED.get());
				}
			});
			Logger.log(LogLevel.WARNING, "Resources", pn + " has declined the resource pack");
		} else if (event.getStatus() == PlayerResourcePackStatusEvent.Status.FAILED_DOWNLOAD){
			player.sendMessage(Message.PACK_FAILED_DOWNLOAD.get());
			Logger.log(LogLevel.WARNING, "Resources", pn + " has failed to download the resource pack");
		} else if (event.getStatus() == PlayerResourcePackStatusEvent.Status.ACCEPTED){
			Logger.log(LogLevel.INFO, "Resources", pn + " has accepted the resource pack");
		} else if (event.getStatus() == PlayerResourcePackStatusEvent.Status.SUCCESSFULLY_LOADED){
			player.sendMessage(Message.PACK_LOADED.get());
			Logger.log(LogLevel.INFO, "Resources", pn + " has successfully loaded the resource pack");
		}
	}

}
