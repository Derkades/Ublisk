package com.robinmc.ublisk.npc;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import com.robinmc.ublisk.utils.NPCUtils;
import com.robinmc.ublisk.utils.Quest;

public class Arzhur {

	/*
	public static void arzhur(Player player){
		NPCUtils api = new NPCUtils();
		String npc = "Arzur";
		UUID uuid = player.getUniqueId();
		PlayerInventory inv = player.getInventory();
		
		if (Config.getBoolean("quest.waterproblem.complete." + uuid)){
			api.msg(player, npc, "Thanks for helping me!");
		} else if (inv.containsAtLeast(new ItemStack(Material.LOG), 5)){
			api.msg(player, npc, "This will do the trick!");
			inv.remove(new ItemStack(Material.LOG, 5));
			player.sendMessage(Messages.questCompleted("Problem with the water", 200));
			Exp.add(player, 200);
			Exp.update(player);
			Config.set("quest.waterproblem.complete." + uuid, true);
		} else if (Config.getBoolean("quest.waterproblem.one." + uuid)){
			api.msg(player, npc, "Oh no, we must fix the dam before it completely breaks. Please collect some wood from the saw and bring it back to me.");
		} else {
			api.msg(player, npc, "People from the village have been complaining about an excessive amount of water, can you go and check the Glaenor Dam?");
			Config.set("quest.waterproblem.one." + uuid, true);
		}	
	}
	*/
	
	public static void arzhur(Player player){
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
