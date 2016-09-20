package com.robinmc.ublisk.quest.npc;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.robinmc.ublisk.enums.Clazz;
import com.robinmc.ublisk.quest.Quest;
import com.robinmc.ublisk.quest.QuestCharacter;
import com.robinmc.ublisk.quest.QuestCharacterClass;
import com.robinmc.ublisk.quest.QuestParticipant;
import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.inventory.BetterInventory;
import com.robinmc.ublisk.weapon.SwordsmanWeapon;

public class Ulric implements QuestCharacterClass {
	
	public void talk(UPlayer player){
		QuestParticipant qp = player.getQuestParticipant(Quest.INTRODUCTION, QuestCharacter.ULRIC);
		BetterInventory inv = qp.getInventory();
		
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
