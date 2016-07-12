package com.robinmc.ublisk.utils;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public enum Quest {
	
	WATER_PROBLEM("Problem with the water", 1000); //FIXME: Fix level
	
	private String name;
	private int level;
	
	Quest(String name, int level){
		this.name = name;
		this.level = level;
	}
	
	public String getName(){
		return name;
	}
	
	public int getLevel(){
		return level;
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
	
	public static int getLevel(Quest quest, int level){
		return quest.getLevel();
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

}
