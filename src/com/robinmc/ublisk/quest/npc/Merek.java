package com.robinmc.ublisk.quest.npc;

import org.bukkit.Material;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.inventory.ItemStack;

import com.robinmc.ublisk.money.MoneyItem;
import com.robinmc.ublisk.quest.NPC;
import com.robinmc.ublisk.quest.NPCInfo;
import com.robinmc.ublisk.quest.NPCInfo.NPCLocation;
import com.robinmc.ublisk.quest.Quest;
import com.robinmc.ublisk.quest.QuestParticipant;
import com.robinmc.ublisk.utils.UPlayer;	

public class Merek extends NPC {
	
	@Override
	public NPCInfo getNPCInfo() {
		return new NPCInfo("Merek", Profession.FARMER, false, new NPCLocation(33, 67, -38));
	}
	
	@Override
	public void talk(UPlayer player){
		QuestParticipant qp = player.getQuestParticipant(Quest.INTRODUCTION, this);
		if (qp.inventoryContains(
				new ItemStack(Material.LOG, 10), 
				new ItemStack(Material.STRING, 16), 
				MoneyItem.DUST.getItem(10))){
			qp.sendMessage("Great! Now go to Ulric to craft a weapon.");
		} else if (qp.inventoryContains(
				new ItemStack(Material.STRING, 16), 
				MoneyItem.DUST.getItem(10))){
			qp.sendMessage("Finally get 10 wood logs at the saw");
		} else if (qp.inventoryContains(
				new ItemStack(Material.WOOL, 4), 
				MoneyItem.DUST.getItem(10))){
			qp.sendMessage("Great job, now please break down your wool into 16 string by using the windmill.");
		} else if (qp.inventoryContains(new ItemStack(Material.GOLD_NUGGET, 10))){
			qp.sendMessage("Now bring me 4 wool by killing sheep.");
		} else {
			qp.sendMessage("What are you doing out here? You don’t even have a weapon yet! I\'ll tell you what you’ll need to make a weapon. First, get 10 gold dust by killing animals.");
		}
			
	}

}
