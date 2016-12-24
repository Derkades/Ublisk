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

public class Alvin extends NPC {
	
	@Override
	public NPCInfo getNPCInfo() {
		return new NPCInfo("Alvin", null, true, new NPCLocation(121.5, 72, 7.3)); // XXX Profession
	}
	
	@Override
	public void talk(UPlayer player) {
		QuestParticipant qp = player.getQuestParticipant(Quest.WATER_PROBLEM, this);
		PlayerInventory inv = qp.getInventory();
		
		if (qp.getQuestCompleted()){
			qp.sendMessage("I'm looking for the biggest rose in the world!");
			return;
		}
		
		if (qp.getProgress(QuestProgress.DAM_REPORTED_BACK) && inv.contains(Material.LOG, 5)){
			//If the player has 5 wood logs (last step)
			qp.sendMessage("This will do the trick!");
			inv.remove(new ItemStack(Material.LOG, 5)); //Remove the 5 wood logs
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
