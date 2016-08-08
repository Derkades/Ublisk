package com.robinmc.ublisk.npc;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.robinmc.ublisk.utils.inventory.BetterInventory;
import com.robinmc.ublisk.utils.quest.Quest;
import com.robinmc.ublisk.utils.quest.QuestCharacter;
import com.robinmc.ublisk.utils.quest.QuestCharacterClass;
import com.robinmc.ublisk.utils.quest.QuestParticipant;
import com.robinmc.ublisk.utils.quest.QuestProgress;

public class Rasmus implements QuestCharacterClass {
	
	public void talk2(Player player){
		QuestParticipant qp = new QuestParticipant(player, Quest.HAY_TRANSPORT, QuestCharacter.RASMUS);
		if (qp.getQuestCompleted()){
			//If player has completed 'Hay Transportation', do quest 'Chicken Hunt'.
			chickenHunt(player);
		} else {
			hayTransportation(player);
		}
	}
	
	private void chickenHunt(Player player){
		QuestParticipant qp = new QuestParticipant(player, Quest.CHICKEN_HUNT, QuestCharacter.RASMUS);
		BetterInventory inv = qp.getInventory();
		
		if (!qp.hasRequiredLevel()){
			//Message
			return;
		}
		
		if (qp.getQuestCompleted()){
			// TODO Message if player has completed quest
		} else if (qp.getProgress(QuestProgress.CHICKEN_HUNT_TALK_TO_ARZHUR) && inv.contains(Material.FEATHER, 15)){
			qp.msg("Thanks for helping! Here is some money for your hard work. Btw you can keep the (something)"); // TODO Tool name
			qp.giveRewardExp();
			qp.sendCompletedMessage();
			qp.setQuestCompleted(true);
		} else if (qp.getProgress(QuestProgress.CHICKEN_HUNT_TALK_TO_ARZHUR)){
			
		} else {
			qp.msg("Hi again. Have you noticed all these monsters eating our crops! Can you maybe slay these monsters for me? Go to Arzhur he probably has a tool for you that will help you!");
			qp.saveProgress(QuestProgress.CHICKENHUNT_TALK_TO_RASMUS);
		}
		
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
