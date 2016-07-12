package com.robinmc.ublisk.npc;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.robinmc.ublisk.utils.NPCUtils;
import com.robinmc.ublisk.utils.Quests;

public class Merek {
	
	public static void merek(Player player){
		NPCUtils npc = Quests.getNPCApi();
		if (Quests.playerHasItem(player, Material.LOG, 10) 
				&& Quests.playerHasItem(player, Material.STRING, 16)
				&& Quests.playerHasItem(player, Material.GOLD_NUGGET, 10)){
			npc.msg(player, "Merek", "Great! Now go to Ulric to craft a weapon.");
		} else if (Quests.playerHasItem(player, Material.STRING, 16)
				&& Quests.playerHasItem(player, Material.GOLD_NUGGET, 10)){
			npc.msg(player, "Merek", "Finally get 10 wood logs at the saw");
		} else if (Quests.playerHasItem(player, Material.WOOL, 4)
				&& Quests.playerHasItem(player, Material.GOLD_NUGGET, 10)){
			npc.msg(player, "Merek", "Great job, now please break down your wool into 16 string by using the windmill.");
		} else if (Quests.playerHasItem(player, Material.GOLD_NUGGET)){
			npc.msg(player, "Merek", "Now bring me 4 wool by killing sheep.");
		} else {
			npc.msg(player, "Merek", "What are you doing out here? You don’t even have a weapon yet! I’ll tell you what you’ll need to make a weapon. First, get 10 golden nuggets by killing animals.");
		}
			
	}

}
