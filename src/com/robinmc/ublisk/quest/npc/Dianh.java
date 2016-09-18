package com.robinmc.ublisk.quest.npc;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.robinmc.ublisk.quest.Quest;
import com.robinmc.ublisk.quest.QuestCharacter;
import com.robinmc.ublisk.quest.QuestCharacterClass;
import com.robinmc.ublisk.quest.QuestParticipant;
import com.robinmc.ublisk.quest.QuestProgress;
import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.inventory.BetterInventory;

public class Dianh implements QuestCharacterClass {

	@Override
	public void talk(UPlayer player2) {
		// TODO Update to UPlayer
		Player player = player2.getPlayer();
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
