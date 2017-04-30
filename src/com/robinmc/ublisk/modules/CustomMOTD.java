package com.robinmc.ublisk.modules;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.server.ServerListPingEvent;

import com.robinmc.ublisk.utils.java.ListUtils;

public class CustomMOTD extends UModule {

	public static final MOTD[] MOTD_LIST = {
			new MOTD(ChatColor.GREEN + "" + ChatColor.STRIKETHROUGH + "Wynncraft", ""),
			new MOTD(ChatColor.MAGIC + "OIJAMOIDJMOSAIJCDMOAISJM", ChatColor.YELLOW + "Ok"),
			//new MOTD("hi", ""),
			//new MOTD(ChatColor.AQUA + "Ublisk is fantastic.", ChatColor.DARK_AQUA + "It really is!"),
			//new MOTD(ChatColor.RED + "Strong insanity end lands donkey anger attribute dinner series.", ""),
			new MOTD(ChatColor.AQUA + "" + ChatColor.BOLD + "HEY! DUBBELKLIKKEN! NU!!", ""),
			new MOTD("Ik zweer, volgende vakantie is het wel af!", ""),
			new MOTD("oeblisk", ""),
			new MOTD("Al gaat Josh nou wat bouwen, is de server sneller af.", ""),
			new MOTD("Ublisk - Waar bungalowhuisjes middeleeuws zijn.", ""),
	};
	
	@EventHandler
	public void onPing(ServerListPingEvent event){
		event.setMotd(ListUtils.getRandomValueFromArray(MOTD_LIST).getMotd());
	}
	
	public static class MOTD {
		
		private String firstLine;
		private String secondLine;
		
		MOTD(String firstLine, String secondLine) {
			this.firstLine = firstLine;
			this.secondLine = secondLine;
		}
		
		private String getMotd() {
			return (firstLine + "\n" + secondLine).replace("&", "\u00A7");
		}
	}

}
