package com.robinmc.ublisk.quest.npc;

import org.bukkit.Material;

import com.robinmc.ublisk.quest.Quest;
import com.robinmc.ublisk.quest.QuestCharacter;
import com.robinmc.ublisk.quest.QuestCharacterClass;
import com.robinmc.ublisk.quest.QuestParticipant;
import com.robinmc.ublisk.quest.QuestProgress;
import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.inventory.BetterInventory;

public class Zoltar implements QuestCharacterClass {

	@Override
	public void talk(UPlayer player) {
		QuestParticipant qp = player.getQuestParticipant(Quest.HAY_TRANSPORT, QuestCharacter.ZOLTAR);
		BetterInventory inv = qp.getInventory();
		
		if (qp.getProgress(QuestProgress.HAY_TRANSPORT_STARTED) && inv.contains(Material.HAY_BLOCK, 10)){
		inv.remove(Material.HAY_BLOCK, 10);
		qp.sendMessage("There you are! That took you a while, didn’t it. Anyway, thanks for helping.");
		qp.sendCompletedMessage(); //Send a message
		qp.giveRewardExp(); //Give reward experience
		qp.setQuestCompleted(true); //Set the quest as completed for this player
		qp.sendMessage("Hold on! If you ever need to sell something, come to me I am always here to buy your goodies.");
		} else {
			qp.sendMessage("Hello, I'm the junk merchant.");
		}
	}

}
