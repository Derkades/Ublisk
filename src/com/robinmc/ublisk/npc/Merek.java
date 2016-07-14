package com.robinmc.ublisk.npc;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.robinmc.ublisk.utils.NPCUtils;
import com.robinmc.ublisk.utils.Quest;	

public class Merek {
	
	public static void merek(Player player){
		NPCUtils npc = Quest.getNPCApi();
		//if (Quest.playerHasItem(player, Material.LOG, 10) 
		//		&& Quest.playerHasItem(player, Material.STRING, 16)
		//		&& Quest.playerHasItem(player, Material.GOLD_NUGGET, 10)){
		if (Quest.playerHasItems(player, new ItemStack(Material.LOG, 10), new ItemStack(Material.STRING, 16), new ItemStack(Material.GOLD_NUGGET, 10))){
			npc.msg(player, "Merek", "Great! Now go to Ulric to craft a weapon.");
		//} else if (Quest.playerHasItem(player, Material.STRING, 16)
		//		&& Quest.playerHasItem(player, Material.GOLD_NUGGET, 10)){
		} else if (Quest.playerHasItems(player, new ItemStack(Material.STRING, 16), new ItemStack(Material.GOLD_NUGGET, 10))){
			npc.msg(player, "Merek", "Finally get 10 wood logs at the saw");
		//} else if (Quest.playerHasItem(player, Material.WOOL, 4)
		//		&& Quest.playerHasItem(player, Material.GOLD_NUGGET, 10)){
		} else if (Quest.playerHasItems(player, new ItemStack(Material.WOOL, 4), new ItemStack(Material.GOLD_NUGGET, 10))){
			npc.msg(player, "Merek", "Great job, now please break down your wool into 16 string by using the windmill.");
		} else if (Quest.playerHasItem(player, Material.GOLD_NUGGET)){
			npc.msg(player, "Merek", "Now bring me 4 wool by killing sheep.");
		} else {
			npc.msg(player, "Merek", "What are you doing out here? You don’t even have a weapon yet! I’ll tell you what you’ll need to make a weapon. First, get 10 golden nuggets by killing animals.");
		}
			
	}

}
