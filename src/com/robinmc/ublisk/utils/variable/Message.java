package com.robinmc.ublisk.utils.variable;

import static org.bukkit.ChatColor.GOLD;
import static org.bukkit.ChatColor.GRAY;
import static org.bukkit.ChatColor.RED;
import static org.bukkit.ChatColor.YELLOW;

public enum Message {
	
	NOT_A_PLAYER(prefix() + RED + "You must be a player in order to execute this command!"),
	WRONG_USAGE(prefix() + RED + "Wrong usage! Type /help for help"),
	REPORT_FORUMS(prefix() + "Please report hackers and staff abusers over at http://ublisk.robinmc.com"),
	SUGGEST_FEATURE(prefix() + "Please suggest new features over at http://ublisk.robinmc.com"),
	NO_PERMISSION(prefix() + RED + "You don't have the required permissions to execute this command"),
	PLAYER_NOT_FOUND(prefix() + RED + "This player could not be found."),
	CANT_PM_MUTED(prefix() + RED + "You cannot send private messages while muted."),
	
	//SONG_ENDED(GOLD + "Music" + GRAY + " >> " + "Your song has ended. Please select a new song using /music"),
	MUSIC_DISABLED(/* GOLD + "Music" + GRAY + " >> " */ prefix("Music") + "Music has been disabled. After this song no more songs will play."),
	MUSIC_ENABLED(/* GOLD + "Music" + GRAY + " >> " */ prefix("Music") + "Music has been enabled"),
	
	PACK_DECLINED(RED + "Please enable server resource packs and join again"),
	PACK_FAILED_DOWNLOAD(prefix() + RED + "We failed in sending you our resource pack. You'll have to play without. Please report this issue at the forums."),
	PACK_LOADED(prefix() + "The resource pack has been successfully loaded"),
	PACK_SENDING(prefix() + "Sending you our resource pack..."),
	
	CLASS_COOLDOWN(prefix() + "You have to wait 15 minutes before you can change class again"),
	CLASS_WRONG_WEAPON(prefix() + "This weapon is not for your class"),
	CLASS_JOIN(prefix() + "Please choose a class. You can always change class later without losing anything"),
	
	FRIEND_NOT_EXIST(prefix("Friends") + RED + "The friend you tried to remove does not exist"),
	FRIEND_OFFLINE(prefix("Friends") + RED + "The friend you tried to add is not online"),
	FRIEND_HEALTH_DISABLED(prefix("Friends") + "You will no longer see your friend's health"),
	FRIEND_HEALTH_ENABLED(prefix("Friends") + "You will now see your friend's health at the top of your screen"),
	
	ERROR_MENU(prefix("Menu") + RED + "An unexpected error has occured. Please report this error and the steps you took to get this error at the forums."),
	ERROR_GENERAL(prefix() + RED + "An unexpected error has occured. Please report this error and the steps you took to get this error at the forums."),
	
	CANT_EAT(prefix() + RED + "Eating food is not allowed on this server. Please use a recycler"),
	ENTITIES_REMOVED(prefix() + YELLOW + "All mobs and items have been cleared!");
	
	private String msg;
	
	Message(String msg){
		this.msg = msg;
	}
	
	public String get(){
		return msg;
	}
	
	static String prefix(){
		return GOLD + "Ublisk" + GRAY + " >> " + YELLOW;
	}
	
	static String prefix(String string){
		return GOLD + string + GRAY + " >> " + YELLOW;
	}
	
}
