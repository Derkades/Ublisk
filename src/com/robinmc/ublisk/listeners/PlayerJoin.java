package com.robinmc.ublisk.listeners;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.inventivetalent.rpapi.ResourcePackAPI;

import com.robinmc.ublisk.HashMaps;
import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.Messages;
import com.robinmc.ublisk.NPCs;
import com.robinmc.ublisk.Var;
import com.robinmc.ublisk.utils.Config;
import com.robinmc.ublisk.utils.Console;

import de.inventivegames.npc.NPC;
import de.inventivegames.npc.NPCLib;

public class PlayerJoin implements Listener {
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event){
		final Player player = event.getPlayer();
		String pn = player.getName();
		
		if (!player.isOp()){
			player.setGameMode(GameMode.ADVENTURE); //If player is not opped, set player gamemode to adventure
		}
		
		Console.sendCommand("scoreboard teams join all " + pn); //Join team "all". This team disables 1.9 collision
		
		event.setJoinMessage(Messages.playerJoin(pn));
		
		player.sendMessage(Messages.sendingPack());
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable(){ 
			//For some reason sending the pack has to be delayed, otherwise the client won't get the message
			public void run(){
				ResourcePackAPI.setResourcepack(player, Var.pack());
			}
		}, 1*20);
		
		//Cache player uuid and name for later use
		Config.set("uuid.uuid." + pn, player.getUniqueId().toString());
		Config.set("uuid.name." + player.getUniqueId(), pn);
		
		HashMaps.addPlayerToMaps(player);
		
		NPCs.despawnAll();
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable(){
			public void run(){
				NPCs.spawnAll();
			}
		}, 10);
	}
	
}
