package com.robinmc.ublisk;

import java.security.SecureRandom;

import org.bukkit.ChatColor;

public enum Tip {
	
	ONE("Tip one"),
	TWO("Tip two");
	
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
