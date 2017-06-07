package xyz.derkades.ublisk.modules;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.server.ServerListPingEvent;

import xyz.derkades.ublisk.utils.java.ListUtils;

public class CustomMOTD extends UModule {

	public static final MOTD[] MOTD_LIST = {
			new MOTD(ChatColor.GREEN + "" + ChatColor.STRIKETHROUGH + "Wynncraft"),
			new MOTD(ChatColor.MAGIC + "OIJAMOIDJMOSAIJCDMOAISJM", ChatColor.YELLOW + "Ok"),
			new MOTD(ChatColor.AQUA + "" + ChatColor.BOLD + "HEY! DUBBELKLIKKEN! NU!!"),
			new MOTD("Ik zweer, volgende vakantie is het wel af!"),
			new MOTD("oeblisk"),
			new MOTD("Al gaat Josh nou wat bouwen, is de server sneller af."),
			new MOTD("Ublisk - Waar bungalowhuisjes middeleeuws zijn."),
			new MOTD("May contain traces of salt."),
			new MOTD("'bijna af'"),
			new MOTD("Moet ik een leuke grap vertellen? Mesa *cough* *cough*"),
			new MOTD("Where more time is spent launching fireworks than placing blocks"),
			new MOTD("Where everything is poopy brown"),
			new MOTD("'Maar je hebt nooit gezegd dat ik moet stoppen, alleen dat ik moet oprotten.' - Jerrijn"),
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
		
		MOTD(String firstLine){
			this.firstLine = firstLine;
			this.secondLine = "";
		}
		
		private String getMotd() {
			return (firstLine + "\n" + secondLine).replace("&", "\u00A7");
		}
	}

}
