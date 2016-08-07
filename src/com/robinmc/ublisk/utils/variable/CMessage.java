package com.robinmc.ublisk.utils.variable;

import static org.bukkit.ChatColor.BOLD;
import static org.bukkit.ChatColor.GOLD;
import static org.bukkit.ChatColor.GRAY;
import static org.bukkit.ChatColor.RED;
import static org.bukkit.ChatColor.RESET;
import static org.bukkit.ChatColor.YELLOW;

public class CMessage { //If you're wondering "Why CMessage?", it's stands for Complicated Message. By this I mean a message that needs additional input
	
	private static String prefix = Message.prefix();
	
	private static String prefix(String string){
		return Message.prefix(string);
	}
	
	//----------------------------- Join and quit -----------------------------//
	
	public static String playerJoin(String pn){
		return prefix + YELLOW + pn + " has joined";
	}	
	
	public static String quit(String pn){
		return prefix + YELLOW + pn + " has left";
	}
	
	//----------------------------- Commands -----------------------------//
	
	public static String userNotFound(String user){
		return prefix + RED + "No additional information was found for user " + user;
	}
	
	public static String nowAfk(String name){
		return prefix + YELLOW + name + " is now AFK";
	}
	
	public static String noLongerAfk(String name){
		return prefix + YELLOW + name + " is no longer AFK";
	}
	
	public static String mutedOther(String pn){
		return prefix("Chat") + pn + " has been muted.";
	}
	
	public static String unMutedOther(String pn){
		return prefix("Chat") + pn + " has been unmuted.";
	}
	
	public static String muted(String pn){
		return prefix("Chat") + "You have been muted by " + pn;
	}
	
	public static String unMuted(String pn){
		return prefix("Chat") + "You have been unmuted by " + pn;
	}
	
	public static String softMutedOther(String pn){
		return prefix("Chat") + pn + " has been softmuted.";
	}
	
	public static String unSoftMutedOther(String pn){
		return prefix("Chat") + pn + " has been un-soft-muted.";
	}
	
	public static String softMuted(String pn){
		return prefix("Chat") + "You have been soft-muted by " + pn;
	}
	
	public static String unSoftMuted(String pn){
		return prefix("Chat") + "You have been un-soft-muted by " + pn;
	}
	
	//----------------------------- Friends -----------------------------//
	
	public static String friendAdded(String pn){
		return prefix("Friends") + pn + " has been added to your friends list";
	}
	
	public static String friendRemoved(String pn){
		return prefix("Friends") + pn + " has been removed from your friends list";
	}
	
	//----------------------------- Music -----------------------------//
	
	public static String startSong(String song){
		return GOLD + "Music" + GRAY + " >> " + "You are now playing " + song;
	}
	
	//----------------------------- Classes ----------------------------- //
	
	public static String changedClass(String c){
		return prefix + "You have changed your class to " + c;
	}	
	
	//----------------------------- Quests and NPC -----------------------------//
	
	public static String questCompleted(String quest, int xp){
		return prefix + "You have completed quest " + BOLD + quest + RESET + YELLOW + " and got " + xp + " XP!";
	}
	
	public static String npcNotFound(String name){
		return prefix("NPC") + RED + "No dialog could be found for an npc with name " + name + ", please report this error.";
	}	
	
	public static String npcMsg(String npc, String message){
		return prefix(npc) + message;
	}
	
	//----------------------------- Miscellaneous -----------------------------//
	
	public static String removeMobsWarning(int sec){
		return prefix + "Clearing all mobs and items in " + sec + " seconds!";
	}
	
	public static String commandLog(String pn, String cmd){
		return prefix("CommandLog") + pn + ": " + cmd;
	}
	
	public static String lootSpawned(int x, int y, int z){
		return prefix("Loot") + "A loot chest has been spawned at " + GOLD + "" + BOLD + x + " " + y + " " + z + RESET + YELLOW + "!";
	}

}
