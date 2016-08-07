package com.robinmc.ublisk.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.robinmc.ublisk.utils.exception.NPCNotFoundException;
import com.robinmc.ublisk.utils.quest.CharacterTalkEvent;
import com.robinmc.ublisk.utils.quest.NPCUtils;
import com.robinmc.ublisk.utils.quest.QuestCharacter;
import com.robinmc.ublisk.utils.variable.CMessage;

public class CharacterTalk implements Listener {
	
	@EventHandler
	public void onTalk(CharacterTalkEvent event){
		
		if (event.isCancelled()){
			return;
		}
		
		Player player = event.getPlayer();
		QuestCharacter npc = event.getNPC();
		try {
			new NPCUtils().talk(player, npc);
		} catch (NPCNotFoundException e) {
			player.sendMessage(CMessage.npcNotFound(npc.getName()));
		}
	}
	
}
