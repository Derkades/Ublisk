package com.robinmc.ublisk;

import org.bukkit.ChatColor;

import com.robinmc.ublisk.utils.java.EnumUtils;

public enum Tip {
	
	ONE("Change class using /class (Note: you can only switch class every 15 minutes)"),
	TWO("Please set your in-game language to US English to enable custom item names"),
	//THREE("Support the server and activate double XP for one minute for free: http://ublisk.robinmc.com/double-xp (adfly)"), // TODO Put this in a better place
	FOUR("Are you noticing lag? Vote for a restart using /voterestart"),
	SEVEN("Enable or disable music by right clicking the chest"),
	EIGHT("Toggle showing friend's health in the friends menu"),
	NINE("Add a friend using /friend add [player]"),
	TEN("Open your friends menu using /friend");
	
	private String tip;
	
	Tip(String tip){
		this.tip = tip;
	}
	
	public String getText(){
		return tip;
	}
	
	public static String getRandomTip(){
		Tip tip = EnumUtils.getRandomEnum(Tip.class);
		String text = tip.getText();
		return ChatColor.GOLD + text;
	}

}
