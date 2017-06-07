package xyz.derkades.ublisk.quest.npc;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Villager.Profession;

import xyz.derkades.ublisk.Var;
import xyz.derkades.ublisk.quest.NPC;
import xyz.derkades.ublisk.quest.Quest;
import xyz.derkades.ublisk.quest.QuestParticipant;
import xyz.derkades.ublisk.quest.QuestProgress;
import xyz.derkades.ublisk.utils.UPlayer;
import xyz.derkades.ublisk.utils.inventory.UInventory;

public class Alvin extends NPC {
	
	@Override
	public String getName() {
		return "Alvin";
	}

	@Override
	public Location getLocation() {
		return new Location(Var.WORLD, 121.5, 72, 7.3);
	}

	@Override
	public Profession getProfession() {
		return null;
	}

	@Override
	public boolean canWalk() {
		return true;
	}
	
	@Override
	public void talk(UPlayer player) {
		QuestParticipant qp = player.getQuestParticipant(Quest.WATER_PROBLEM, this);
		UInventory inv = qp.getInventory();
		
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
