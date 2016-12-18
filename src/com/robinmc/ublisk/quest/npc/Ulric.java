package com.robinmc.ublisk.quest.npc;

import org.bukkit.Material;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.inventory.ItemStack;

import com.robinmc.ublisk.Clazz;
import com.robinmc.ublisk.quest.NPC;
import com.robinmc.ublisk.quest.NPCInfo;
import com.robinmc.ublisk.quest.NPCInfo.NPCLocation;
import com.robinmc.ublisk.quest.Quest;
import com.robinmc.ublisk.quest.QuestParticipant;
import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.inventory.UInventory;
import com.robinmc.ublisk.weapon.SwordsmanWeapon;

public class Ulric extends NPC {
	
	@Override
	public NPCInfo getNPCInfo() {
		return new NPCInfo("Ulric", Profession.FARMER, false, new NPCLocation(38.5, 67, -26.5));
	}
	
	@Override
	public void talk(UPlayer player){
		QuestParticipant qp = player.getQuestParticipant(Quest.INTRODUCTION, this);
		UInventory inv = qp.getInventory();
		
		if (inv.contains(new ItemStack(Material.LOG, 10),
			new ItemStack(Material.STRING, 16),
			new ItemStack(Material.GOLD_NUGGET, 10))){
			Clazz c = player.getClazz();
			if (c == Clazz.SWORDSMAN){
				player.sendMessage("As of right now, only swordsman is implemented. Please choose swordsman from the classes menu");
				return;
			}
			
			inv.addWeapon(SwordsmanWeapon.BASIC_WOODEN_SWORD);
			inv.remove(Material.LOG, 10);
			inv.remove(Material.STRING, 16);
			inv.remove(Material.GOLD_NUGGET, 10);
		} else {
			qp.sendMessage("I can make a weapon for you if you bring me the required materials.");
		}
	}

}
