package com.robinmc.ublisk.listeners;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.robinmc.ublisk.HashMaps;
import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.npc.Merek;
import com.robinmc.ublisk.utils.Console;

import de.inventivegames.npc.event.NPCInteractEvent;
import de.inventivegames.npc.event.NPCInteractEvent.InteractType;

public class NPCInteract implements Listener {
	
	@EventHandler
	public void npcInteract(NPCInteractEvent event){
		Player player = event.getPlayer();
		final UUID uuid = player.getUniqueId();
		Console.sendMessage("Trigger!");
		if (!HashMaps.cooldownnpc.get(uuid)){
			Console.sendMessage("Cooldown!");
			InteractType type = event.getType();
			if (type == InteractType.RIGHT_CLICK){
				HashMaps.cooldownnpc.put(uuid, true);
				String name = event.getNPC().getName();
				Console.sendMessage("[NPC] " + player.getName() + " right clicked npc " + name);
				
				Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable(){
					public void run(){
						HashMaps.cooldownnpc.put(uuid, false);
					}
				}, 5*20);
				
				if (name == "Merek"){
					Merek.merek(player);
				}
				
			}
		}
	}

}
