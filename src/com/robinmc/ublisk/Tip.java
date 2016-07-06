package com.robinmc.ublisk;

import java.security.SecureRandom;

import org.bukkit.ChatColor;

public enum Tip {
	
	ONE("Change class using /class (Note: you can only switch class every 15 minutes)"),
	TWO("Please set your in-game language to US English to enable custom item names"),
	THREE("Support the server and activate double XP for one minute for free: http://ublisk.robinmc.com/double-xp (adfly)"),
	FOUR("Check out our map over at http://maps.robinmc.com"),
	FIVE("Download our android app! http://ublisk.robinmc.com/ublisk.apk"),
	SIX("Found a bug? Please report http://ublisk.robinmc.com/report-bug");
	
	private String tip;
	
	Tip(String tip){
		this.tip = tip;
	}
	
	public String getText(){
		return tip;
	}
	
	private static <T extends Enum<?>> T randomEnum(Class<T> clazz){
		final SecureRandom random = new SecureRandom();
        int x = random.nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    }
	
	public static String getRandom(){
		Tip tip = randomEnum(Tip.class);
		String text = tip.getText();
		return ChatColor.GOLD + "Tip " + ChatColor.GRAY + ">> " + ChatColor.YELLOW + text;
	}

}
