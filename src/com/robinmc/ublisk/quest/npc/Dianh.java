package com.robinmc.ublisk.quest.npc;

import org.bukkit.Material;

import com.robinmc.ublisk.quest.NPC;
import com.robinmc.ublisk.quest.NPCInfo;
import com.robinmc.ublisk.quest.Quest;
import com.robinmc.ublisk.quest.QuestParticipant;
import com.robinmc.ublisk.quest.QuestProgress;
import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.inventory.BetterInventory;

public class Dianh extends NPC {

	@Override
	public NPCInfo getNPCInfo() {
		return new NPCInfo("Dianh", null, false, null); // TODO Dianh coordinates XXX Profession
	}
	
	@Override
	public void talk(UPlayer player) {
		QuestParticipant qp = player.getQuestParticipant(Quest.SEARCH_MEAT, this);
		BetterInventory inv = qp.getInventory();
		
		if(qp.getProgress(QuestProgress.SEARCH_FOR_MEAT_TALK_TO_ARZHUR) && inv.contains(Material.ROTTEN_FLESH, 20)){
			qp.sendMessage("Hi there! I am Dianh, do you have anything for me to purify? I do this daily, people come up to me and want me to purify water and food to make sure it's healthy. Well anyway, let’s start the purification.");
			inv.remove(Material.ROTTEN_FLESH, 20);
			inv.add(Material.GRILLED_PORK, 10);
		} else {
			qp.sendMessage("Hello there, what are you looking at?!");
		}
	}

}
