package com.robinmc.ublisk;

import org.bukkit.ChatColor;

public class Messages {
	
	public static String noPlayer(){
		return ChatColor.GOLD + "Ublisk" + ChatColor.GRAY + " >> " + ChatColor.RED + "You must be a player in order to execute this command!";
	}
	
	public static String songEnded(){
		return ChatColor.GOLD + "Music" + ChatColor.GRAY + " >> " + ChatColor.YELLOW + "Your song has ended. Please select a new song using /music";
	}
	
	public static String playerJoin(String pn){
		return ChatColor.GOLD + "Ublisk" + ChatColor.GRAY + " >> " + ChatColor.YELLOW + pn + " has joined";
	}
	
	public static String menuErrorWrongItem(){
		return ChatColor.GOLD + "Menu" + ChatColor.GRAY + " >> " + ChatColor.RED + "An unexpected error has occured. Please report this error and the steps you took to get this error at the forums.";
	}
	
	public static String cantEat(String pn){
		return ChatColor.GOLD + "Ublisk" + ChatColor.GRAY + " >> " + ChatColor.RED + pn + " Eating food is not allowed on this server";
	}
	
	public static String startSong(String song){
		return ChatColor.GOLD + "Music" + ChatColor.GRAY + " >> " + ChatColor.YELLOW + "You are now playing " + song;
	}
	
	public static String wrongUsage(){
		return ChatColor.GOLD + "Ublisk" + ChatColor.GRAY + " >> " + ChatColor.RED + "Wrong usage! Type /help for help";
	}
	
	public static String tntDetect(){
		return ChatColor.GOLD + "Ublisk" + ChatColor.GRAY + " >> " + ChatColor.RED + "TNT has been detected in your inventory. Using it will result in a 30s temp-ban";
	}
	
	public static String tntBan(){
		return ChatColor.RED + "You have been banned for using TNT. You will be unbanned automatically in 30 seconds.";
	}
	
	public static String removeMobsWarning(int sec){
		return ChatColor.GOLD + "Ublisk" + ChatColor.GRAY + " >> " + ChatColor.YELLOW + "Clearing all mobs and items in " + sec + " seconds!";
	}
	
	public static String removedMobs(){
		return ChatColor.GOLD + "Ublisk" + ChatColor.GRAY + " >> " + ChatColor.YELLOW + "All mobs and items have been cleared!";
	}
	
}
