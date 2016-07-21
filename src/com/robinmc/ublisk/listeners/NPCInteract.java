package com.robinmc.ublisk.listeners;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.robinmc.ublisk.HashMaps;
import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.utils.Console;
import com.robinmc.ublisk.utils.quest.NPCUtils;

import de.inventivegames.npc.event.NPCInteractEvent;
import de.inventivegames.npc.event.NPCInteractEvent.InteractType;

public class NPCInteract implements Listener {
	
	@EventHandler
	public void npcInteract(NPCInteractEvent event){
		
		if (event.isCancelled()){
			return;
		}
		
		Player player = event.getPlayer();
		final UUID uuid = player.getUniqueId();
		if (!HashMaps.cooldownnpc.get(uuid)){
			InteractType type = event.getType();
			if (type == InteractType.RIGHT_CLICK){
				HashMaps.cooldownnpc.put(uuid, true);
				String name = event.getNPC().getName();
				Console.sendMessage("[NPC] " + player.getName() + " right clicked npc " + name);
				
				Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable(){
					public void run(){
						HashMaps.cooldownnpc.put(uuid, false);
					}
				}, 2*20);
				
				new NPCUtils().open(player, name);
			}
		}
	}

}
