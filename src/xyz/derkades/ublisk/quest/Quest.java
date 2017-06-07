package xyz.derkades.ublisk.quest;

public enum Quest {
	
	UNKNOWN("Unknown", 0, 0, "unknown", 0),
	
	//FIXME: Fix reward xp
	INTRODUCTION("Introduction", 0, 0, "introduction", 0),
	WATER_PROBLEM("Problem with the water", 5, 1, "water-problem", 0),
	HAY_TRANSPORT("Hay transportation", 10, 1, "hay-transport", 0),
	CHICKEN_HUNT("Chicken hunt", 10, 1, "chicken-hunt", 0),
	SEARCH_MEAT("Search for meat", 12, 1, "search-meat", 5),
	BEYOND_GLAENOR("Beyond glaenor", 15, 1, "beyond-glaenor", 0),
	ORE_SHORTAGE("Shortage of ores", 20, 1, "ore-shortage", 0),
	KING_ORDER("Order of the king", 25, 1, "king-order", 0),
	SUSPICIOUS_MOVEMENTS("Suspicious movements", 30, 1, "suspicious-movements", 0);
	
	private String name;
	private int xp;
	private int rewardExp;
	private String configString;
	private int life;
	
	Quest(String name, int level, int rewardExp, String config, int lifeCrystalAmount){
		this.name = name;
		this.xp = level;
		this.rewardExp = rewardExp;
		this.configString = config;
		this.life = lifeCrystalAmount;
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
	
	public int getLifeCrystalReward(){
		return life;
	}
	
	public boolean noLifeReward(){
		return life == 0;
	}
	
	public static Quest fromConfigString(String s){
		for (Quest quest : Quest.values()){
			if (quest.getConfigString() == s){
				return quest;
			}
		}
		return null;
	}
	
	@Override
	public String toString(){
		return name;
	}
	
}
