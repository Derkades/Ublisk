package com.robinmc.ublisk.quest;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import com.robinmc.ublisk.Message;
import com.robinmc.ublisk.utils.UPlayer;

public class NPCClickListener implements Listener {
	
	@EventHandler(priority = EventPriority.LOW)
	public void onClick(PlayerInteractEntityEvent event){
		Entity entity = event.getRightClicked();
		
		if (!(entity instanceof Villager)){
			return;
		}
		
		event.setCancelled(true);
		
		UPlayer player = new UPlayer(event);
		
		NPC npc = NPC.fromName(entity.getCustomName());
		
		if (npc == null){
			player.sendMessage(Message.Complicated.Quests.npcNotFound(entity.getCustomName()));
			return;
		}
			
		npc.talk(player);
	}

}
