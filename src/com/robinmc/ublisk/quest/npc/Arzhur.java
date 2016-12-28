package com.robinmc.ublisk.quest.npc;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.robinmc.ublisk.quest.NPC;
import com.robinmc.ublisk.quest.NPCInfo;
import com.robinmc.ublisk.quest.NPCInfo.NPCLocation;
import com.robinmc.ublisk.quest.Quest;
import com.robinmc.ublisk.quest.QuestParticipant;
import com.robinmc.ublisk.quest.QuestProgress;
import com.robinmc.ublisk.utils.UPlayer;

public class Arzhur extends NPC {

	@Override
	public NPCInfo getNPCInfo() {
		return new NPCInfo("Arzhur", null, false, new NPCLocation(111.5, 68, -103.5));  // XXX Profession
	}
	
	@Override
	public void talk(UPlayer player) {
		if (player.getQuestParticipant(Quest.SEARCH_MEAT, this).getQuestCompleted()){
			player.getQuestParticipant(Quest.SEARCH_MEAT, this).sendMessage("Nice work! I wish you the best of luck and we will meet again. Soon!");
		} else if (player.getQuestParticipant(Quest.CHICKEN_HUNT, this).getQuestCompleted()){
			searchMeat(player);
		} else if (player.getQuestParticipant(Quest.WATER_PROBLEM, this).getQuestCompleted()){
			chickenHunt(player);
		} else {
			waterProblem(player);
		}
	}
	
	private void searchMeat(UPlayer player){
		QuestParticipant qp = player.getQuestParticipant(Quest.SEARCH_MEAT, this);
		PlayerInventory inv = qp.getInventory();
		if (inv.containsAtLeast(new ItemStack(Material.GRILLED_PORK), 10)){
			qp.sendMessage("Thank you very much for helping us. We will be alright for a while! Here is something that will help you survive in the fields.");
			inv.remove(new ItemStack(Material.GRILLED_PORK, 10));
			qp.setLifeCrystals(qp.getLifeCrystals() + 5);
			qp.setQuestCompleted(true);
			qp.sendCompletedMessage();
			qp.giveRewardExp();
		} else {
			qp.sendMessage("We are having a shortage of food here in town. Meat in particular. can you maybe go and get some Zombie Flesh by slaying some Zombies at the Ruins. Go to Dianh, she can purify the meat so we can eat it.");
			qp.saveProgress(QuestProgress.SEARCH_FOR_MEAT_TALK_TO_ARZHUR);
		}
	}
	
	private void chickenHunt(UPlayer player){
		QuestParticipant qp = player.getQuestParticipant(Quest.CHICKEN_HUNT, this);
	
		if (qp.getProgress(QuestProgress.CHICKENHUNT_TALK_TO_RASMUS)){	
			qp.sendMessage("You were sent by Rasmus, weren’t you? That old man always bothers himself of the so called monsters in his farm. Just between you and me, he has gotten a little crazy over the last few years and now thinks that the chickens in his farm are monsters! Here take this. It will help you to scare those chickens away.");
			qp.saveProgress(QuestProgress.CHICKEN_HUNT_TALK_TO_ARZHUR);
		} else {
			qp.sendMessage("Thank you for fixing up the dam!");
		}
	}
	
	private void waterProblem(UPlayer player){
		QuestParticipant qp = player.getQuestParticipant(Quest.WATER_PROBLEM, this);
		if (qp.getProgress(QuestProgress.CHECKED_DAM)){ 
			//If the player has checked the dam
			qp.sendMessage("Oh no, we must fix the dam before it completely breaks. You should collect some wood from the saw and bring it to Alvin.");
			qp.saveProgress(QuestProgress.DAM_REPORTED_BACK);
		} else {
			//If neither of the above are true
			qp.sendMessage("People from the village have been complaining about an excessive amount of water, can you go and check the Glaenor Dam?");
			if (!qp.getProgress(QuestProgress.DAM_FIRST_TALK)) 
				qp.saveProgress(QuestProgress.DAM_FIRST_TALK);
		}
	}

}
