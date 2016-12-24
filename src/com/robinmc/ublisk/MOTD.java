package com.robinmc.ublisk;

import org.bukkit.ChatColor;

import com.robinmc.ublisk.utils.java.EnumUtils;

public enum MOTD {
	
	MOTD_1(ChatColor.GREEN + "" + ChatColor.STRIKETHROUGH + "Wynncraft", ""),
	MOTD_2(ChatColor.MAGIC + "OIJAMOIDJMOSAIJCDMOAISJM", ChatColor.YELLOW + "Ok"),
	MOTD_3("Hello human being reading this!", "Why have you not joined yet!?!?");
	
	private String lineOne;
	private String lineTwo;
	
	MOTD(String lineOne, String lineTwo){
		this.lineOne = lineOne;
		this.lineTwo = lineTwo;
	}
	
	private String getMotd(){
		return (lineOne + "\n" + lineTwo)
				.replace("&", "\u00A7");
	}
	
	public static String getRandomMotd(){
		MOTD motd = EnumUtils.getRandomEnum(MOTD.class);
		return motd.getMotd();
	}

}
