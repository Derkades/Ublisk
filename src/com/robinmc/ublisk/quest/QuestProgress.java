package com.robinmc.ublisk.quest;

public enum QuestProgress {
	
	CHECKED_DAM,
	DAM_FIRST_TALK,
	
	HAY_TRANSPORT_STARTED,
	HAY_DELIVERED,
	
	CHICKENHUNT_TALK_TO_RASMUS,
	CHICKEN_HUNT_TALK_TO_ARZHUR,
	
	SEARCH_FOR_MEAT_TALK_TO_ARZHUR,
	;
	
	public static QuestProgress fromString(String s){
		return QuestProgress.valueOf(s);
	}
	
	public static String toString(QuestProgress progress){
		return progress.toString();
	}
	

}
