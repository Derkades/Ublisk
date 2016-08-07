package com.robinmc.ublisk.npc;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.robinmc.ublisk.utils.inventory.BetterInventory;
import com.robinmc.ublisk.utils.quest.Quest;
import com.robinmc.ublisk.utils.quest.QuestCharacter;
import com.robinmc.ublisk.utils.quest.QuestCharacterClass;
import com.robinmc.ublisk.utils.quest.QuestParticipant;
import com.robinmc.ublisk.utils.quest.QuestProgress;

public class Arzhur implements QuestCharacterClass {

	@Override
	public void talk(Player player) {
		QuestParticipant qp = new QuestParticipant(player, Quest.WATER_PROBLEM, QuestCharacter.ARZHUR);
		BetterInventory inv = qp.getInventory();
		
		if (qp.getQuestCompleted()){
			//If the player has already completed the quest
			qp.msg("Thank you for helping me!");
		} else if (inv.contains(Material.LOG, 5)){
			//If the player has 5 wood logs (last step)
			qp.msg("This will do the trick!");
			inv.remove(Material.LOG, 5); //Remove the 5 wood logs
			qp.sendCompletedMessage(); //Send a message
			qp.giveRewardExp(); //Give reward experience
			qp.setQuestCompleted(true); //Set the quest as completed for this player
		} else if (qp.getProgress(QuestProgress.CHECKED_DAM)){ //TODO: NPC to go to to obtain "checked-dam" status
			//If the player has checked the dam
			qp.msg("Oh no, we must fix the dam before it completely breaks. Please collect some wood from the saw and bring it back to me.");
		} else {
			//If neither of the above are true
			qp.msg("People from the village have been complaining about an excessive amount of water, can you go and check the Glaenor Dam?");
			if (!qp.getProgress(QuestProgress.DAM_FIRST_TALK)) 
				qp.saveProgress(QuestProgress.DAM_FIRST_TALK);
		}
	}

}
