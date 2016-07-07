package com.robinmc.ublisk.npc;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.robinmc.ublisk.utils.NPCUtils;

public class Merek {
	
	public static void merek(Player player){
		NPCUtils npc = new NPCUtils();
		PlayerInventory inv = player.getInventory();
		
		if (	inv.containsAtLeast(new ItemStack(Material.LOG), 10) &&
				inv.containsAtLeast(new ItemStack(Material.STRING), 10) &&
				inv.containsAtLeast(new ItemStack(Material.GOLD_NUGGET), 10)){
			npc.msg(player, "Merek", "Great! Now go to Ulric to craft a weapon.");
		} else if (inv.containsAtLeast(new ItemStack(Material.STRING), 16) && inv.containsAtLeast(new ItemStack(Material.GOLD_NUGGET), 10)){
			npc.msg(player, "Merek", "Finally get 10 wood logs at the saw");
		} else if (inv.containsAtLeast(new ItemStack(Material.WOOL), 4) && inv.containsAtLeast(new ItemStack(Material.GOLD_NUGGET), 10)){
			npc.msg(player, "Merek", "Great job, now please break down your wool into 16 string by using the windmill.");
		} else if (inv.containsAtLeast(new ItemStack(Material.GOLD_NUGGET), 10)){
			npc.msg(player, "Merek", "Now bring me 4 wool by killing sheep.");
		} else {
			npc.msg(player, "Merek", "What are you doing out here? You don’t even have a weapon yet! I’ll tell you what you’ll need to make a weapon. First, get 10 golden nuggets by killing animals.");
		}
			
		
	}

}
