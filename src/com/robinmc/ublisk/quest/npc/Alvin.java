package com.robinmc.ublisk.quest.npc;

import org.bukkit.Material;

import com.robinmc.ublisk.quest.Quest;
import com.robinmc.ublisk.quest.QuestCharacter;
import com.robinmc.ublisk.quest.QuestCharacterClass;
import com.robinmc.ublisk.quest.QuestParticipant;
import com.robinmc.ublisk.quest.QuestProgress;
import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.inventory.BetterInventory;

public class Alvin implements QuestCharacterClass{

	@Override
	public void talk(UPlayer player) {
		//QuestParticipant qp = new QuestParticipant(player, Quest.WATER_PROBLEM, QuestCharacter.ALVIN);
		QuestParticipant qp = player.getQuestParticipant(Quest.WATER_PROBLEM, QuestCharacter.ALVIN);
		BetterInventory inv = player.getInventory();
		
		if (qp.getQuestCompleted()){
			qp.sendMessage("I'm looking for the biggest rose in the world!");
			return;
		}
		
		if (qp.getProgress(QuestProgress.DAM_REPORTED_BACK) && inv.contains(Material.LOG, 5)){
			//If the player has 5 wood logs (last step)
			qp.sendMessage("This will do the trick!");
			inv.remove(Material.LOG, 5); //Remove the 5 wood logs
			qp.sendCompletedMessage(); //Send a message
			qp.giveRewardExp(); //Give reward experience
			qp.setQuestCompleted(true); //Set the quest as completed for this player
			return;
		}
		
		if (qp.getProgress(QuestProgress.CHECKED_DAM)){
			qp.sendMessage("You should go to back to Gleanor and tell them this dam is broken right now!");
			return;
		}
		
		if (!qp.getProgress(QuestProgress.DAM_FIRST_TALK)){
			qp.sendMessage("I'm looking for the biggest rose in the world!");
			return;
		}
		
		qp.sendMessage("HELP! I don't know what to do! Everything will be underwater! Please save us! ");
		qp.saveProgress(QuestProgress.CHECKED_DAM);
		
		
	}

}
