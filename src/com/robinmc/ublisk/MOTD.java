package com.robinmc.ublisk;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import com.robinmc.ublisk.utils.java.EnumUtils;

public enum MOTD implements Listener {

	MOTD_1(ChatColor.GREEN + "" + ChatColor.STRIKETHROUGH + "Wynncraft", ""),
	MOTD_2(ChatColor.MAGIC + "OIJAMOIDJMOSAIJCDMOAISJM", ChatColor.YELLOW + "Ok"),
	MOTD_3("hi", ""),
	MOTD_4(ChatColor.AQUA + "Ublisk is fantastic.", ChatColor.DARK_AQUA + "It really is!"),
	MOTD_5(ChatColor.RED + "Strong insanity end lands donkey anger attribute dinner series.", "Yes, very much.")
	
	;

	private String lineOne;
	private String lineTwo;

	MOTD(String lineOne, String lineTwo) {
		this.lineOne = lineOne;
		this.lineTwo = lineTwo;
	}

	private String getMotd() {
		return (lineOne + "\n" + lineTwo).replace("&", "\u00A7");
	}

	private static String getRandomMotd() {
		MOTD motd = EnumUtils.getRandomEnum(MOTD.class);
		return motd.getMotd();
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void onServerPing(ServerListPingEvent event){
		event.setMotd(getRandomMotd());
	}

}
