package com.robinmc.ublisk.quest.npc;

import org.bukkit.Material;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.robinmc.ublisk.Clazz;
import com.robinmc.ublisk.quest.NPC;
import com.robinmc.ublisk.quest.NPCInfo;
import com.robinmc.ublisk.quest.NPCInfo.NPCLocation;
import com.robinmc.ublisk.quest.Quest;
import com.robinmc.ublisk.quest.QuestParticipant;
import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.weapons.sword.wood.BasicWoodenSword;

public class Ulric extends NPC {
	
	@Override
	public NPCInfo getNPCInfo() {
		return new NPCInfo("Ulric", Profession.FARMER, false, new NPCLocation(38.5, 67, -26.5));
	}
	
	@Override
	public void talk(UPlayer player){
		QuestParticipant qp = player.getQuestParticipant(Quest.INTRODUCTION, this);
		PlayerInventory inv = qp.getInventory();
		
		if (qp.inventoryContains(new ItemStack(Material.LOG, 10),
			new ItemStack(Material.STRING, 16),
			new ItemStack(Material.GOLD_NUGGET, 10))){
			
			if (player.getClazz() != Clazz.SWORDSMAN){
				player.sendMessage("As of right now, only swordsman is implemented. Please choose swordsman from the classes menu");
				return;
			}
			
			inv.addItem(new BasicWoodenSword().getItemStack());
			inv.remove(new ItemStack(Material.LOG, 10));
			inv.remove(new ItemStack(Material.STRING, 16));
			inv.remove(new ItemStack(Material.GOLD_NUGGET, 10));
		} else {
			qp.sendMessage("I can make a weapon for you if you bring me the required materials.");
		}
	}

}
