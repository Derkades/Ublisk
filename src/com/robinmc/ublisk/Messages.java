package com.robinmc.ublisk;

import org.bukkit.ChatColor;

public class Messages {
	
	static ChatColor gold = ChatColor.GOLD;
	static ChatColor gray = ChatColor.GRAY;
	static ChatColor red = ChatColor.RED;
	static ChatColor yellow = ChatColor.YELLOW;
	
	private static String prefix = gold + "Ublisk" + gray + " >> " + yellow;
	
	private static String prefix(String string){
		return gold + string + gray + " >> " + yellow;
	}
	
	//----------------------------- Join and quit -----------------------------//
	
	public static String playerJoin(String pn){
		return prefix + yellow + pn + " has joined";
	}	
	
	public static String quit(String pn){
		return prefix + yellow + pn + " has left";
	}
	
	//----------------------------- Commands -----------------------------//
	
	public static String noPlayer(){
		return prefix + red + "You must be a player in order to execute this command!";
	}
	
	public static String wrongUsage(){
		return prefix + red + "Wrong usage! Type /help for help";
	}
		
	public static String userNotFound(String user){
		return prefix + red + "No additional information was found for user " + user;
	}
	
	public static String reportForums(){
		return prefix + yellow + "Please report hackers and staff abusers over at http://robinmc.com";
	}
	
	public static String suggestFeature(){
		return prefix + yellow + "Please suggest new features over at http://robinmc.com";
	}
	
	public static String nowAfk(String name){
		return prefix + yellow + name + " is now AFK";
	}
	
	public static String noLongerAfk(String name){
		return prefix + yellow + name + " is no longer AFK";
	}
	
	public static String noPermission(String rank){
		return prefix + red + "You don't have permission to execute this command, you need at least rank " + rank + ".";
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
		return prefix + red + "We failed in sending you our resource pack. You'll have to play without. Please report this issue at the forums.";
	}
	
	public static String packLoaded(){
		return prefix + yellow + "The resource pack has been successfully loaded";
	}
	
	public static String sendingPack(){
		return prefix + yellow + "Sending you our resource pack...";
	}
	
	//----------------------------- Classes ----------------------------- //
	
	public static String changedClass(String c){
		return prefix + yellow + "You have changed your class to " + c;
	}
	
	public static String classCooldown(){
		return prefix + yellow + "You have to wait 15 minutes before you can change class again";
	}
	
	public static String wrongWeapon(){
		return prefix + yellow + "This weapon is not for your class";
	}
	
	//----------------------------- Quests and NPC -----------------------------//
	
	public static String questCompleted(String quest, int xp){
		return prefix + yellow + "You have completed quest " + ChatColor.BOLD + quest + ChatColor.RESET + yellow + " and got " + xp + " XP!";
	}
	
	public static String npcNotFound(String name){
		return gold + "NPC" + gray + " >> " + red + "No dialog could be found for an npc with name " + name + ", please report this error.";
	}
	
	//----------------------------- Friends -----------------------------//
	
	public static String friendAdded(String pn){
		return prefix("Friends") + pn + " has been added to your friends list";
	}
	
	public static String friendRemoved(String pn){
		return prefix("Friends") + pn + " has been removed from your friends list";
	}
	
	public static String friendNotExist(){
		return prefix("Friends") + red + " the friend you tried to remove does not exist";
	}
	
	public static String friendAddNotOnline(){
		return prefix("Friends") + red + " the friend you tried to add is not online";
	}
	
	//----------------------------- Miscellaneous -----------------------------//
	
	public static String menuErrorWrongItem(){
		return gold + "Menu" + gray + " >> " + red + "An unexpected error has occured. Please report this error and the steps you took to get this error at the forums.";
	}
	
	public static String cantEat(String pn){
		return prefix + red + pn + " Eating food is not allowed on this server. Please use a recycler";
	}
	
	public static String removeMobsWarning(int sec){
		return prefix + yellow + "Clearing all mobs and items in " + sec + " seconds!";
	}
	
	public static String removedMobs(){
		return prefix + yellow + "All mobs and items have been cleared!";
	}
	
	public static String npcMsg(String npc, String message){
		return gold + npc + gray + " >> " + yellow + message;
	}
	
	public static String commandLog(String pn, String cmd){
		return gold + "CommandLog" + gray + " >> " + yellow + pn + ": " + cmd;
	}
	
	public static String generalError(){
		return prefix + red + "An unexpected error has occured. Please report this error and the steps you took to get this error at the forums.";
	}
	
	public static String lootSpawned(int x, int y, int z){
		return prefix("Loot") + "A loot chest has been spawned at " + gold + "" + ChatColor.BOLD + x + " " + y + " " + z + ChatColor.RESET + yellow + "!";
	}
	
}
