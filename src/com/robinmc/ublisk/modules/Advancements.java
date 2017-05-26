package com.robinmc.ublisk.modules;

import org.bukkit.entity.Player;

import com.robinmc.ublisk.utils.Ublisk;

public class Advancements extends UModule {
	
	public static void grantAdvancement(Player player, Advancement advancement){
		Ublisk.sendConsoleCommand("advancement grant " + player.getName() + " until " + advancement.toString());
	}
	
	public static enum Advancement {
		
		QUESTS_ROOT("quests/root");
		
		private String path;
		
		Advancement(String path){
			this.path = path;
		}
		
		@Override
		public String toString(){
			return "ublisk:" + path;
		}
		
	}

}
