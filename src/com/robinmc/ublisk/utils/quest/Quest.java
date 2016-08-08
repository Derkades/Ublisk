package com.robinmc.ublisk.utils.quest;

import com.robinmc.ublisk.utils.exception.QuestNotFoundException;

public enum Quest {
	
	UNKNOWN("Unknown", 0, 0, "unknown"),
	//FIXME: Fix reward xp
	INTRODUCTION("Introduction", 0, 0, "introduction"),
	WATER_PROBLEM("Problem with the water", 5, 1, "water-problem"),
	HAY_TRANSPORT("Hay transportation", 10, 1, "hay-transport"),
	CHICKEN_HUNT("Chicken hunt", 10, 1, "chicken-hunt"),
	SEARCH_MEAT("Search for meat", 12, 1, "search-meat"),
	BEYOND_GLAENOR("Beyond glaenor", 15, 1, "beyond-glaenor"),
	ORE_SHORTAGE("Shortage of ores", 20, 1, "ore-shortage"),
	KING_ORDER("Order of the king", 25, 1, "king-order"),
	SUSPICIOUS_MOVEMENTS("Suspicious movements", 30, 1, "suspicious-movements");
	
	private String name;
	private int xp;
	private int rewardExp;
	private String configString;
	
	Quest(String name, int level, int rewardExp, String config){
		this.name = name;
		this.xp = level;
		this.rewardExp = rewardExp;
		this.configString = config;
	}
	
	public String getName(){
		return name;
	}
	
	public int getLevel(){
		return xp;
	}
	
	public int getRewardExp(){
		return rewardExp;
	}
	
	public String getConfigString(){
		return configString;
	}
	
	@Deprecated
	public static String getName(Quest quest){
		return quest.getName();
	}
	
	public static Quest fromConfigString(String s) throws QuestNotFoundException{
		for (Quest quest : Quest.values()){
			if (quest.getConfigString() == s){
				return quest;
			}
		}
		
		throw new QuestNotFoundException();
	}
	
}
