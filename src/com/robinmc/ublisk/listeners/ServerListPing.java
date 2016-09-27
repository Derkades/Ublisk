package com.robinmc.ublisk.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import com.robinmc.ublisk.MOTD;

public class ServerListPing implements Listener {
	
	@EventHandler
	public void onPing(ServerListPingEvent event){
		event.setMaxPlayers(Bukkit.getOnlinePlayers().size() + 1);
		event.setMotd(MOTD.getRandomMotd());
	}

}
