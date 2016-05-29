package com.robinmc.ublisk.listeners;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.robinmc.ublisk.Messages;
import com.robinmc.ublisk.utils.Console;

public class PlayerJoin implements Listener {
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event){
		Player player = event.getPlayer();
		String pn = player.getName();
		
		player.setGameMode(GameMode.ADVENTURE); //Set player gamemode to adventure
		
		Console.sendCommand("scoreboard teams join all " + pn); //Join team "all". This team disables 1.9 collision
		
		event.setJoinMessage(Messages.playerJoin(pn));
	}
	
}
