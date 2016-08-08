package com.robinmc.ublisk.npc;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.robinmc.ublisk.utils.inventory.BetterInventory;
import com.robinmc.ublisk.utils.quest.Quest;
import com.robinmc.ublisk.utils.quest.QuestCharacter;
import com.robinmc.ublisk.utils.quest.QuestCharacterClass;
import com.robinmc.ublisk.utils.quest.QuestParticipant;
import com.robinmc.ublisk.utils.quest.QuestProgress;

public class Zoltar implements QuestCharacterClass {

	@Override
	public void talk(Player player) {
		QuestParticipant haytransport = new QuestParticipant(player, Quest.HAY_TRANSPORT, QuestCharacter.ZOLTAR);
		BetterInventory inv = haytransport.getInventory();
		
		if(haytransport.getProgress(QuestProgress.HAY_TRANSPORT_STARTED) && inv.contains(Material.HAY_BLOCK, 10))
		{
		inv.remove(Material.HAY_BLOCK, 10);
		haytransport.msg("There you are! That took you a while, didn’t it. Anyway, thanks for helping.");
		haytransport.sendCompletedMessage(); //Send a message
		haytransport.giveRewardExp(); //Give reward experience
		haytransport.setQuestCompleted(true); //Set the quest as completed for this player
		haytransport.msg("Hold on! If you ever need to sell something, come to me I am always here to buy your goodies.");
		} else {
			haytransport.msg("Hello, I'm the junk merchant.");
		}
	}

}
