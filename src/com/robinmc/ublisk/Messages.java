package com.robinmc.ublisk;

import org.bukkit.ChatColor;

public class Messages {
	
	static ChatColor gold = ChatColor.GOLD;
	static ChatColor gray = ChatColor.GRAY;
	static ChatColor red = ChatColor.RED;
	static ChatColor yellow = ChatColor.YELLOW;
	
	//----------------------------- Join and quit -----------------------------//
	
	public static String playerJoin(String pn){
		return gold + "Ublisk" + gray + " >> " + yellow + pn + " has joined";
	}	
	
	public static String quit(String pn){
		return gold + "Ublisk" + gray + " >> " + yellow + pn + " has left";
	}
	
	//----------------------------- Commands -----------------------------//
	
	public static String noPlayer(){
		return gold + "Ublisk" + gray + " >> " + red + "You must be a player in order to execute this command!";
	}
	
	public static String wrongUsage(){
		return gold + "Ublisk" + gray + " >> " + red + "Wrong usage! Type /help for help";
	}
		
	public static String userNotFound(String user){
		return gold + "Ublisk" + gray + " >> " + red + "No additional information was found for user " + user;
	}
	
	public static String reportForums(){
		return gold + "Ublisk" + gray + " >> " + yellow + "Please report hackers and staff abusers over at http://robinmc.com";
	}
	
	public static String suggestFeature(){
		return gold + "Ublisk" + gray + " >> " + yellow + "Please suggest new features over at http://robinmc.com";
	}
	
	public static String nowAfk(String name){
		return gold + "Ublisk" + gray + " >> " + yellow + name + " is now AFK";
	}
	
	public static String noLongerAfk(String name){
		return gold + "Ublisk" + gray + " >> " + yellow + name + " is no longer AFK";
	}
	
	public static String noPermission(String rank){
		return gold + "Ublisk" + gray + " >> " + red + "You don't have permission to execute this command, you need at least rank " + rank + ".";
	}
	
	//----------------------------- Music -----------------------------//
	
	public static String songEnded(){
		return gold + "Music" + gray + " >> " + yellow + "Your song has ended. Please select a new song using /music";
	}
	
	public static String startSong(String song){
		return gold + "Music" + gray + " >> " + yellow + "You are now playing " + song;
	}
	
	public static String musicDisabled(){
		return gold + "Music" + gray + " >> " + yellow + "Music has been disabled. After this song no more songs will play.";
	}
	
	public static String musicEnabled(){
		return gold + "Music" + gray + " >> " + yellow + "Music has been re-enabled";
	}
	
	//----------------------------- Resource pack -----------------------------//
	
	public static String declinedPack(){
		return red + "Please enable server resource packs and join again";
	}
	
	public static String packFailedDownload(){
		return gold + "Ublisk" + gray + " >> " + red + "We failed in sending you our resource pack. You'll have to play without. Please report this issue at the forums.";
	}
	
	public static String packLoaded(){
		return gold + "Ublisk" + gray + " >> " + yellow + "The resource pack has been successfully loaded";
	}
	
	public static String sendingPack(){
		return gold + "Ublisk" + gray + " >> " + yellow + "Sending you our resource pack...";
	}
	
	//----------------------------- Classes ----------------------------- //
	
	public static String changedClass(String c){
		return gold + "Ublisk" + gray + " >> " + yellow + "You have changed your class to " + c;
	}
	
	public static String classCooldown(){
		return gold + "Ublisk" + gray + " >> " + yellow + "You have to wait 15 minutes before you can change class again";
	}
	
	public static String wrongWeapon(){
		return gold + "Ublisk" + gray + " >> " + yellow + "This weapon is not for your class";
	}
	
	//----------------------------- Quests and NPC -----------------------------//
	
	public static String questCompleted(String quest, int xp){
		return gold + "Ublisk" + gray + " >> " + yellow + "You have completed quest " + ChatColor.BOLD + quest + ChatColor.RESET + yellow + " and got " + xp + " XP!";
	}
	
	public static String npcNotFound(String name){
		return gold + "NPC" + gray + " >> " + red + "No dialog could be found for an npc with name " + name + " please report this error.";
	}
	
	//----------------------------- Miscellaneous -----------------------------//
	
	public static String menuErrorWrongItem(){
		return gold + "Menu" + gray + " >> " + red + "An unexpected error has occured. Please report this error and the steps you took to get this error at the forums.";
	}
	
	public static String cantEat(String pn){
		return gold + "Ublisk" + gray + " >> " + red + pn + " Eating food is not allowed on this server. Please use a recycler";
	}
	
	public static String removeMobsWarning(int sec){
		return gold + "Ublisk" + gray + " >> " + yellow + "Clearing all mobs and items in " + sec + " seconds!";
	}
	
	public static String removedMobs(){
		return gold + "Ublisk" + gray + " >> " + yellow + "All mobs and items have been cleared!";
	}
	
	public static String npcMsg(String npc, String message){
		return  gold + npc + gray + " >> " + yellow + message;
	}
	
	public static String commandLog(String pn, String cmd){
		return gold + "CommandLog" + gray + " >> " + yellow + pn + " : " + cmd;
	}
	
	public static String generalError(){
		return gold + "Ublisk" + gray + " >> " + red + "An unexpected error has occured. Please report this error and the steps you took to get this error at the forums.";
	}
	
}
