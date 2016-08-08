package com.robinmc.ublisk.listeners;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.inventivetalent.npclib.event.NPCInteractEvent;

import com.robinmc.ublisk.HashMaps;
import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.utils.Console;
import com.robinmc.ublisk.utils.exception.NPCNotFoundException;
import com.robinmc.ublisk.utils.quest.NPCUtils;
import com.robinmc.ublisk.utils.quest.QuestCharacter;
import com.robinmc.ublisk.utils.variable.CMessage;

public class NPCInteract implements Listener {
	
	@EventHandler
	public void npcInteract(NPCInteractEvent event){		
		Player player = event.getPlayer();
		final UUID uuid = player.getUniqueId();
		
		if (HashMaps.cooldownNpc.get(uuid)){
			return;
		}
		
		String name = event.getNpc().getName();
		
		NPCUtils api = new NPCUtils();
		
		try {
			api.talk(player, QuestCharacter.fromString(name));
		} catch (IllegalArgumentException | NPCNotFoundException e) {
			player.sendMessage(CMessage.npcNotFound(name));
		}
		
		Console.sendMessage("[NPC] " + player.getName() + " right clicked npc " + name);
		
		HashMaps.cooldownNpc.put(uuid, true);
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable(){
			public void run(){
				HashMaps.cooldownNpc.put(uuid, false);
			}
		}, 2*20);
	}

}
