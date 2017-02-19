package com.robinmc.ublisk.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class ServerListPing implements Listener {
	
	@EventHandler
	public void onPing(ServerListPingEvent event){
		event.setMaxPlayers(Bukkit.getOnlinePlayers().size() + 1);
	}

}
