package com.robinmc.ublisk.listeners;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.inventivetalent.npclib.event.NPCInteractEvent;

import com.robinmc.ublisk.HashMaps;
import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.utils.Console;
import com.robinmc.ublisk.utils.quest.CharacterTalkEvent;
import com.robinmc.ublisk.utils.quest.QuestCharacter;

public class NPCInteract implements Listener {
	
	@EventHandler
	public void npcInteract(NPCInteractEvent event){
		
		if (event.isCancelled()){
			return;
		}
		
		Player player = event.getPlayer();
		final UUID uuid = player.getUniqueId();
		
		if (HashMaps.cooldownNpc.get(uuid)){
			return;
		}
		
		if (!(event.getNpc().getEntityType() == EntityType.PLAYER)){
			return;
		}
			
		String name = event.getNpc().getName();
		CharacterTalkEvent talkEvent = new CharacterTalkEvent(player, QuestCharacter.valueOf(name));
		Bukkit.getServer().getPluginManager().callEvent(talkEvent);
		
		if (talkEvent.isCancelled()){
			event.setCancelled(true);
			return;
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
