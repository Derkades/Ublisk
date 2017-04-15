package com.robinmc.ublisk.quest.npc;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.inventory.PlayerInventory;

import com.robinmc.ublisk.Message;
import com.robinmc.ublisk.quest.NPC;
import com.robinmc.ublisk.quest.Quest;
import com.robinmc.ublisk.quest.QuestParticipant;
import com.robinmc.ublisk.quest.QuestProgress;
import com.robinmc.ublisk.utils.UPlayer;

public class Rasmus extends NPC {
	
	@Override
	public String getName() {
		return "Rasmus";
	}

	@Override
	public Location getLocation() {
		return null;
	}

	@Override
	public Profession getProfession() {
		return null;
	}

	@Override
	public boolean canWalk() {
		return false;
	}
	
	@Override
	public void talk(UPlayer player){
		QuestParticipant qp = player.getQuestParticipant(Quest.HAY_TRANSPORT, this);
		if (qp.getQuestCompleted()){
			//If player has completed 'Hay Transportation', do quest 'Chicken Hunt'.
			chickenHunt(player);
		} else {
			hayTransportation(player);
		}
	}
	
	private void chickenHunt(UPlayer player){
		QuestParticipant qp = player.getQuestParticipant(Quest.CHICKEN_HUNT, this);
		PlayerInventory inv = qp.getInventory();
		
		if (!qp.hasRequiredLevel()){
			qp.sendMessage(Message.QUEST_LOW_LEVEL);
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
			// TODO Message for chicken hunt quest
		} else {
			qp.sendMessage("Hi again. Have you noticed all these monsters eating our crops! Can you kill these monsters for me? Go to Arzhur, he probably has a tool for you that will help you!");
			qp.saveProgress(QuestProgress.CHICKENHUNT_TALK_TO_RASMUS);
		}
		
	}
	
	private void hayTransportation(UPlayer player){
		QuestParticipant qp = player.getQuestParticipant(Quest.HAY_TRANSPORT, this);

		qp.sendMessage("I see you have fixed the water issue, well done! Can you maybe help me too? There is a big pile of hay that needs to be transported to a cart just outside of Glaenor. You should give it to Zoltar, he\'ll pay you for the job.");
		
		if (!qp.getProgress(QuestProgress.HAY_TRANSPORT_STARTED)) 
			qp.saveProgress(QuestProgress.HAY_TRANSPORT_STARTED);
		// TODO Item for breaking hay
	}

}
