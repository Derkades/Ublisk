package com.robinmc.ublisk.npc;

import org.bukkit.entity.Player;

import com.robinmc.ublisk.utils.quest.NPCUtils;
import com.robinmc.ublisk.utils.quest.Quest;

public class Rasmus {
	
	private	static NPCUtils api = Quest.getNPCApi();
	private static String npc = "Rasmus";
	
	public void main(Player player){
		if (Quest.getQuestCompleted(player, Quest.HAY_TRANSPORT)){
			//When player has completed this quest, do quest "Chicken Hunt" next.
			chickenHunt(player);
		} else {
			hayTransportation(player);
		}
	}
	
	private void chickenHunt(Player player){
		//Quest quest = Quest.CHICKEN_HUNT;
		api.msg(player, npc, "");
	}
	
	private void hayTransportation(Player player){
		//Quest quest = Quest.HAY_TRANSPORT;
		api.msg(player, npc, "");
	}

}
