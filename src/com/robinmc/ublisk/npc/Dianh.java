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
		// TODO Auto-generated method stub
		QuestParticipant searchMeat = new QuestParticipant(player, Quest.SEARCH_MEAT, QuestCharacter.DIANH);
		BetterInventory inv = searchMeat.getInventory();
		
		if(searchMeat.getProgress(QuestProgress.SEARCH_FOR_MEAT_TALK_TO_ARZHUR) && inv.contains(Material.ROTTEN_FLESH, 20))
		{
			searchMeat.msg("Hi there! I am Dianh, do you have anything for me to purify? I do this daily, people come up to me and want me to purify water and food to make sure it is healthy. Also you can turn your items back into 50% of the costs such as weapons and armor or in this case food! Well anyway, let’s start the purification.");
			inv.remove(Material.ROTTEN_FLESH, 20);
			inv.add(Material.GRILLED_PORK, 10);
		} else {
			// TODO [Sander] Missing text
		}
		
	}

}
