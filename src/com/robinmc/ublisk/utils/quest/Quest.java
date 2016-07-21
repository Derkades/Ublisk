package com.robinmc.ublisk.utils.quest;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.robinmc.ublisk.utils.Config;
import com.robinmc.ublisk.utils.Exp;
import com.robinmc.ublisk.utils.variable.CMessage;

public enum Quest {
	
	WATER_PROBLEM("Problem with the water", 5),
	HAY_TRANSPORT("Hay transportation", 10),
	CHICKEN_HUNT("Chicken hunt", 10),
	SEARCH_MEAT("Search for meat", 12),
	BEYOND_GLAENOR("Beyond glaenor", 15),
	ORE_SHORTAGE("Shortage of ores", 20),
	KING_ORDER("Order of the king", 25),
	SUSPICIOUS_MOVEMENTS("Suspicious movements", 30);
	
	private String name;
	private int xp;
	
	Quest(String name, int level){
		this.name = name;
		this.xp = level;
	}
	
	public String getName(){
		return name;
	}
	
	public int getExp(){
		return xp;
	}
	
	public static void saveProgress(Player player, Quest quest, String data){
		Config.set("quests." + player.getUniqueId() + "." + quest.getName(), true);
	}
	
	public static boolean getProgress(Player player, Quest quest, String data){
		try {
			return Config.getBoolean("quests." + quest.getName());
		} catch (Exception e){
			return false;
		}
	}
	
	public static void setQuestCompleted(Player player, Quest quest, boolean bool){
		Config.set("quest." + player.getUniqueId() + "." + quest.getName(), bool);
	}
	
	public static boolean getQuestCompleted(Player player, Quest quest){
		return Config.getBoolean("quests." + player.getUniqueId() + "." + quest.getName());
	}
	
	public static boolean playerHasItem(Player player, Material material, int amount){
		PlayerInventory inv = player.getInventory();
		return inv.containsAtLeast(new ItemStack(material), amount);
	}
	
	public static boolean playerHasItem(Player player, Material material){
		return playerHasItem(player, material, 1);
	}
	
	public static boolean playerHasItems(Player player, Material... items){
		boolean hasItems = true;
		for (Material item : items){
			if (!playerHasItem(player, item)){
				hasItems = false;
			}
		}
		return hasItems;
	}
	
	public static boolean playerHasItems(Player player, ItemStack... items){
		boolean hasItems = true;
		for (ItemStack item : items){
			if (!player.getInventory().containsAtLeast(item, item.getAmount())){
				hasItems = false;
			}
		}
		return hasItems;
	}
	
	public static int getExp(Quest quest, int level){
		return quest.getExp();
	}
	
	public static String getName(Quest quest){
		return quest.getName();
	}
	
	public static void removeItems(Player player, Material material, int amount){
		player.getInventory().remove(new ItemStack(material, amount));
	}
	
	public static void removeItem(Player player, Material material){
		removeItems(player, material, 1);
	}
	
	public static NPCUtils getNPCApi(){
		return new NPCUtils();
	}
	
	public static void removeItem(Player player, ItemStack item){
		player.getInventory().remove(item);
	}
	
	public static void removeItems(Player player, ItemStack... stacks){
		for (ItemStack item : stacks){
			removeItem(player, item);
		}
	}
	
	public static void completeQuest(Player player, Quest quest){
		int xp = quest.getExp();
		String name = quest.getName();
		Exp.add(player, xp);
		player.sendMessage(CMessage.questCompleted(name, xp));
		setQuestCompleted(player, quest, true);
	}
	
	public static boolean playerEnoughExp(Player player, Quest quest){
		int level = Exp.getLevel(player);
		int levelRequired = quest.getExp();
		return level >= levelRequired;
	}
	
	public @interface QuestHandler {
		public Quest quest();
	}

}
