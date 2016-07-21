package com.robinmc.ublisk.npc;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.robinmc.ublisk.utils.quest.NPCUtils;
import com.robinmc.ublisk.utils.quest.Quest;

public class Arzhur {
	
	public static void main(Player player){
		NPCUtils api = Quest.getNPCApi();
		String npc = "Arzhur";
		Quest quest = Quest.WATER_PROBLEM;
		
		if (Quest.getQuestCompleted(player, quest)){
			api.msg(player, npc, "Thanks for helping me!");
		} else if (Quest.playerHasItem(player, Material.LOG, 5)){
			api.msg(player, npc, "This will do the trick!");
			Quest.removeItem(player, new ItemStack(Material.LOG, 5));
			Quest.completeQuest(player, quest);
		} else if (Quest.getProgress(player, quest, "checked-dam")){ //TODO: NPC to go to to obtain "checked-dam" status
			api.msg(player, npc, "Oh no, we must fix the dam before it completely breaks. Please collect some wood from the saw and bring it back to me.");
		} else {
			api.msg(player, npc, "People from the village have been complaining about an excessive amount of water, can you go and check the Glaenor Dam?");
			Quest.saveProgress(player, quest, "first-talk");
		}
		
	}

}
