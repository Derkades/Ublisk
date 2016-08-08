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
		if (new QuestParticipant(player, Quest.SEARCH_MEAT, QuestCharacter.ARZHUR).getQuestCompleted()){
			new QuestParticipant(player, Quest.SEARCH_MEAT, QuestCharacter.ARZHUR).msg("Nice work! I wish you the best of luck and we will meet again. Soon!");
		} else if (new QuestParticipant(player, Quest.CHICKEN_HUNT, QuestCharacter.ARZHUR).getQuestCompleted()){
			searchMeat(player);
		} else if (new QuestParticipant(player, Quest.WATER_PROBLEM, QuestCharacter.ARZHUR).getQuestCompleted()){
			chickenHunt(player);
		} else {
			waterProblem(player);
		}
	}
	
	private void searchMeat(Player player){
		QuestParticipant qp = new QuestParticipant(player, Quest.SEARCH_MEAT, QuestCharacter.ARZHUR);
		BetterInventory inv = qp.getInventory();
		if (inv.contains(Material.GRILLED_PORK, 10))
		{
			qp.msg("Thank you very much for helping us. We will be alright for a while! Here is something that will help you survive in the fields.");
			inv.remove(Material.GRILLED_PORK, 10);
			qp.getLifeCrystalPlayer().addLifeCrystals(5);
			qp.setQuestCompleted(true);
			qp.sendCompletedMessage();
			qp.giveRewardExp();
		} else {
			qp.msg("We are having a shortage of food here in town. Meat in particular. can you maybe go and get some Zombie Flesh by slaying some Zombies at the Ruins. Go to Dianh, she can purify the meat so we can eat it.");
			qp.saveProgress(QuestProgress.SEARCH_FOR_MEAT_TALK_TO_ARZHUR);
		}
	}
	
	private void chickenHunt(Player player){
		QuestParticipant qp = new QuestParticipant(player, Quest.CHICKEN_HUNT, QuestCharacter.ARZHUR);
	
		if (qp.getProgress(QuestProgress.CHICKENHUNT_TALK_TO_RASMUS)){	
			qp.msg("You were sent by Rasmus, weren’t you? That old man always bothers himself of the so called monsters in his farm. Just between you and me, he has gotten a little crazy over the last few years and now thinks that the chickens in his farm are monsters! Here take this. It will help you to scare those chickens away.");
			qp.saveProgress(QuestProgress.CHICKEN_HUNT_TALK_TO_ARZHUR);
		} else {
			qp.msg("Hey there. Nice work on the dam, See ya!");
		}
	}
	
	private void waterProblem(Player player){
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
