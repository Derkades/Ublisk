package com.robinmc.ublisk.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.robinmc.ublisk.utils.Console;

import de.inventivegames.npc.event.NPCInteractEvent;
import de.inventivegames.npc.event.NPCInteractEvent.InteractType;

public class NPCInteract implements Listener {
	
	@EventHandler
	public void npcInteract(NPCInteractEvent event){
		InteractType type = event.getType();
		if (type == InteractType.RIGHT_CLICK){
			Player player = event.getPlayer();
			String name = event.getNPC().getName();
			Console.sendMessage("[NPC] " + player.getName() + " right clicked npc " + name);
			player.sendMessage("Hi there!");
		}
	}

}
