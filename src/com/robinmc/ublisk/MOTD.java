package com.robinmc.ublisk;

import com.robinmc.ublisk.utils.java.EnumUtils;

public enum MOTD {
	
	MOTD_1("MOTD 1", ""),
	MOTD_2("MOTD 2", "MOTD 2"),
	MOTD_3("MOTD", "3");
	
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
