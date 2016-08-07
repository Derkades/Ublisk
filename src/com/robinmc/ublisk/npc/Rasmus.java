package com.robinmc.ublisk.npc;

import org.bukkit.entity.Player;

import com.robinmc.ublisk.utils.quest.Quest;
import com.robinmc.ublisk.utils.quest.QuestCharacter;
import com.robinmc.ublisk.utils.quest.QuestCharacterClass;
import com.robinmc.ublisk.utils.quest.QuestParticipant;
import com.robinmc.ublisk.utils.quest.QuestProgress;

public class Rasmus implements QuestCharacterClass {
	
	public void talk(Player player){
		QuestParticipant qp = new QuestParticipant(player, Quest.HAY_TRANSPORT, QuestCharacter.RASMUS);
		if (qp.getQuestCompleted()){
			//If player has completed 'Hay Transportation', do quest 'Chicken Hunt'.
			chickenHunt(player);
		} else {
			hayTransportation(player);
		}
	}
	
	private void chickenHunt(Player player){
		//TODO: Chicken hunt quest
	}
	
	private void hayTransportation(Player player){
		QuestParticipant qp = new QuestParticipant(player, Quest.HAY_TRANSPORT, QuestCharacter.RASMUS);
		if (qp.getProgress(QuestProgress.HAY_DELIVERED)){
			qp.msg("There you are! That took you a while, didn’t it. Anyway, thanks for helping.");
		} else {
			qp.msg("I see you have fixed the water issue, well done! Can you maybe help me too? There is a big pile of hay that needs to be transported to a cart just outside of Glaenor. You should give it to Zoltar, he’ll pay you for the job.");
		}	
	}

}
