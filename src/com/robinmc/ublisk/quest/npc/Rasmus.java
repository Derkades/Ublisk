package com.robinmc.ublisk.quest.npc;

import org.bukkit.Material;

import com.robinmc.ublisk.quest.Quest;
import com.robinmc.ublisk.quest.QuestCharacter;
import com.robinmc.ublisk.quest.QuestCharacterClass;
import com.robinmc.ublisk.quest.QuestParticipant;
import com.robinmc.ublisk.quest.QuestProgress;
import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.inventory.BetterInventory;

public class Rasmus implements QuestCharacterClass {
	
	public void talk(UPlayer player){
		QuestParticipant qp = player.getQuestParticipant(Quest.HAY_TRANSPORT, QuestCharacter.RASMUS);
		if (qp.getQuestCompleted()){
			//If player has completed 'Hay Transportation', do quest 'Chicken Hunt'.
			chickenHunt(player);
		} else {
			hayTransportation(player);
		}
	}
	
	private void chickenHunt(UPlayer player){
		QuestParticipant qp = player.getQuestParticipant(Quest.CHICKEN_HUNT, QuestCharacter.RASMUS);
		BetterInventory inv = qp.getInventory();
		
		if (!qp.hasRequiredLevel()){
			//Message
			return;
		}
		
		if (qp.getQuestCompleted()){
			// TODO Message if player has completed quest
		} else if (qp.getProgress(QuestProgress.CHICKEN_HUNT_TALK_TO_ARZHUR) && inv.contains(Material.FEATHER, 15)){
			qp.sendMessage("Thanks for helping! Here is some money for your hard work. By the way, you can keep the (something)"); // TODO Tool name
			qp.giveRewardExp();
			qp.sendCompletedMessage();
			qp.setQuestCompleted(true);
		} else if (qp.getProgress(QuestProgress.CHICKEN_HUNT_TALK_TO_ARZHUR)){
			// TODO Message
		} else {
			qp.sendMessage("Hi again. Have you noticed all these monsters eating our crops! Can you kill these monsters for me? Go to Arzhur, he probably has a tool for you that will help you!");
			qp.saveProgress(QuestProgress.CHICKENHUNT_TALK_TO_RASMUS);
		}
		
	}
	
	private void hayTransportation(UPlayer player){
		QuestParticipant qp = player.getQuestParticipant(Quest.HAY_TRANSPORT, QuestCharacter.RASMUS);

		qp.sendMessage("I see you have fixed the water issue, well done! Can you maybe help me too? There is a big pile of hay that needs to be transported to a cart just outside of Glaenor. You should give it to Zoltar, he\'ll pay you for the job.");
		
		if (!qp.getProgress(QuestProgress.HAY_TRANSPORT_STARTED)) 
			qp.saveProgress(QuestProgress.HAY_TRANSPORT_STARTED);
		//TODO item voor hay verzamelen
	}

}
