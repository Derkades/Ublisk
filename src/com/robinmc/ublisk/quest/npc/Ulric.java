package com.robinmc.ublisk.quest.npc;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.robinmc.ublisk.enums.Classes;
import com.robinmc.ublisk.quest.Quest;
import com.robinmc.ublisk.quest.QuestCharacter;
import com.robinmc.ublisk.quest.QuestCharacterClass;
import com.robinmc.ublisk.quest.QuestParticipant;
import com.robinmc.ublisk.utils.inventory.BetterInventory;
import com.robinmc.ublisk.utils.variable.Message;

public class Ulric implements QuestCharacterClass {
	
	public void talk(Player player){
		QuestParticipant qp = new QuestParticipant(player, Quest.INTRODUCTION, QuestCharacter.ULRIC);
		BetterInventory inv = qp.getInventory();
		
		if (inv.contains(new ItemStack(Material.LOG, 10),
			new ItemStack(Material.STRING, 16),
			new ItemStack(Material.GOLD_NUGGET, 10))){
			Classes c = Classes.getClass(player);
			if (c == Classes.ARCHER){
				player.sendMessage("Bows are not implemented yet. Please choose another class");
			} else if (c == Classes.SWORDSMAN){
				//Weapon.giveWeapon(player, Weapon.OLD_WOODEN_SWORD);
			} else {
				player.sendMessage(Message.ERROR_GENERAL.get());
			}
		} else {
			qp.msg("I can make a weapon for you if you bring me the required materials.");
		}
	}

}
