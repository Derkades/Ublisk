package com.robinmc.ublisk.npc;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.robinmc.ublisk.utils.inventory.BetterInventory;
import com.robinmc.ublisk.utils.quest.Quest;
import com.robinmc.ublisk.utils.quest.QuestCharacter;
import com.robinmc.ublisk.utils.quest.QuestCharacterClass;
import com.robinmc.ublisk.utils.quest.QuestParticipant;
import com.robinmc.ublisk.utils.quest.QuestProgress;

public class Dianh implements QuestCharacterClass {

	@Override
	public void talk(Player player) {
		QuestParticipant qp = new QuestParticipant(player, Quest.SEARCH_MEAT, QuestCharacter.DIANH);
		BetterInventory inv = qp.getInventory();
		
		if(qp.getProgress(QuestProgress.SEARCH_FOR_MEAT_TALK_TO_ARZHUR) && inv.contains(Material.ROTTEN_FLESH, 20)){
			qp.msg("Hi there! I am Dianh, do you have anything for me to purify? I do this daily, people come up to me and want me to purify water and food to make sure it's healthy. Well anyway, let’s start the purification.");
			inv.remove(Material.ROTTEN_FLESH, 20);
			inv.add(Material.GRILLED_PORK, 10);
		} else {
			qp.msg("Hello there, what are you looking at?!");
		}
	}

}
