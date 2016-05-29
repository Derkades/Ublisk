package com.robinmc.ublisk;

import org.bukkit.ChatColor;

public class Messages {
	
	public static String noPlayer(){
		return ChatColor.GOLD + "Ublisk" + ChatColor.GRAY + " >> " + ChatColor.RED + "You must be a player in order to execute this command!";
	}
	
	public static String songEnded(){
		return ChatColor.GOLD + "Music" + ChatColor.GRAY + " >> " + ChatColor.YELLOW + "MusicYour music has ended. Please select a new song using /music";
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
	
}
