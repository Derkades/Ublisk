package com.robinmc.ublisk;

import static org.bukkit.ChatColor.AQUA;
import static org.bukkit.ChatColor.BOLD;
import static org.bukkit.ChatColor.DARK_AQUA;
import static org.bukkit.ChatColor.GOLD;
import static org.bukkit.ChatColor.GRAY;
import static org.bukkit.ChatColor.RED;
import static org.bukkit.ChatColor.RESET;
import static org.bukkit.ChatColor.YELLOW;

import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.guilds.Guild;

public enum Message {
	
	NOT_A_PLAYER(prefix() + RED + "You must be a player in order to execute this command!"),
	WRONG_USAGE(prefix() + RED + "Wrong usage! Type /help for help"),
	REPORT(prefix() + "Please report hackers and staff abusers over at http://ublisk.robinmc.com"),
	SUGGEST_FEATURE(prefix() + "Please suggest new features here: http://goo.gl/EcrBia"),
	NO_PERMISSION(prefix() + RED + "You don't have the required permissions to execute this command"),
	PLAYER_NOT_FOUND(prefix() + RED + "That player could not be found."),
	
	NOT_IN_GUILD(prefix("Guilds") + RED + "You are not in a guild."),
	ALREADY_IN_GUILD(prefix("Guilds") + RED + "You are already in a guild. Please leave your guild first."),
	GUILD_NOT_INVITED(prefix("Guilds") + RED + "You are not invited to join a guild."),
	GUILD_ALREADY_EXISTS(prefix("Guilds") + RED + "This guild already exists!"),
	INVALID_GUILD_IMAGE_URL(prefix("Guilds") + RED + "Invalid image URL. Make sure your URL starts with http:// and does not contain quotation marks (\' / \")."),
	GUILD_IMAGE_SET(prefix("Guilds") + "You successfully changed your guild's icon. It may take some time to update."),
	
	CANT_PM_MUTED(prefix("Chat") + RED + "You cannot send private messages while muted."),
	CANT_CHAT_MUTED(prefix("Chat") + RED + "You cannot chat while muted."),
	
	MUSIC_DISABLED(prefix("Music") + "Music has been disabled. After this song no more songs will play."),
	MUSIC_ENABLED(prefix("Music") + "Music has been enabled"),
	
	PACK_DECLINED(RED + "You have declined the automatic installation of the server resource pack. We recommend installing our resource pack for the best experience. For information on how to download our resource pack, go to http://ublisk.robinmc.com/pack/."),
	PACK_FAILED_DOWNLOAD(prefix() + RED + "We failed in sending you our resource pack. You'll have to play without. Please report this issue."),
	PACK_LOADED(prefix() + "The resource pack has been successfully loaded."),
	PACK_SENDING(prefix() + "Sending you our resource pack..."),
	PACK_CHECK(prefix() + "If you see a green object, the pack is enabled. If you see a normal guardian, you do not have the resource pack installed. If you see neither, particles are turned off."),
	
	CLASS_COOLDOWN(prefix() + "You have to wait 15 minutes before you can change class again"),
	CLASS_WRONG_WEAPON(prefix() + "This weapon is not for your class"),
	CLASS_JOIN(prefix() + "Please choose a class. You can always change class later without losing anything"),
	
	FRIEND_NOT_EXIST(prefix("Friends") + RED + "The friend you tried to remove does not exist"),
	FRIEND_OFFLINE(prefix("Friends") + RED + "The friend you tried to add is not online"),
	FRIEND_HEALTH_DISABLED(prefix("Friends") + "You will no longer see your friend's health"),
	FRIEND_HEALTH_ENABLED(prefix("Friends") + "You will now see your friend's health at the top of your screen"),
	
	ERROR_MENU(prefix("Menu") + RED + "An unexpected error has occured. Please report this error and the steps you took to get this error at the forums."),
	ERROR_GENERAL(prefix() + RED + "An unexpected error has occured. Please report this error and the steps you took to get this error at the forums."),
	
	CANT_EAT(prefix() + RED + "Eating food is not allowed on this server. Please use a recycler to get rid of the item."),
	
	ENTITIES_REMOVED(prefix() + YELLOW + "All mobs and items have been cleared!"),
	
	QUEST_LOW_LEVEL(prefix() + "You have not yet reached the right level needed to start this quest. Please come back later."),
	
	DOUBLE_XP_COOLDOWN(prefix() + RED + "Hi there, person who tried to activate double xp, there's a cooldown to prevent people like you from abusing the system!"),
	
	BUILDER_MODE_ACTIVATED(prefix() + YELLOW + "You are now in builder mode. When you're done, simply type /builder again to exit out of builder mode and to get your inventory back."),
	BUILDER_MODE_DEACTIVATED(prefix() + YELLOW + "You are no longer in builder mode. Enjoy playing!"),
	
	VOTE_BOX_BUSY(prefix("Voting") + RED + "Someone is already opening a voting box. Please try again in a minute."),
	VOTE_BOX_INSUFFICIENT_POINTS(prefix("Voting") + RED + "You do not have enough voting points. Vote at " + Var.VOTE_URL),
	
	NOT_ENOUGH_MONEY(prefix("Money") + RED + "You don't have enough money"),
	CANT_AFFORD_ITEM(prefix("Money") + RED + "You can't afford that item"),
	INVENTORY_NOT_CONTAIN_MONEY_ITEM(prefix("Money") + RED + "Your inventory does not contain any"),
	
	ALREADY_VOTED_RESTART(prefix() + RED + "You have already voted for a restart"),
	
	ABILITY_NOT_ENOUGH_MANA(prefix("Abilities") + "You do not have enough mana to do this ability."),
	ABILITY_NOT_ENOUGH_LEVEL(prefix("Abilities") + "You have not yet unlocked this ability.");
	
	private String msg;
	
	Message(String msg){
		this.msg = msg;
	}
	
	public String get(){
		return msg;
	}
	
	static String prefix(){
		return prefix("Ublisk");
	}
	
	public static String prefix(String string){
		return GOLD + string + GRAY + " >> " + YELLOW;
	}
	
	public static class Complicated {
		
		public static String startSong(String song){
			return GOLD + "Music" + GRAY + " >> " + "You are now playing " + song;
		}
		
		public static String changedClass(String c){
			return prefix() + "You have changed your class to " + c;
		}	

		public static String removeMobsWarning(int sec){
			return prefix() + "Clearing all mobs and items in " + sec + " seconds!";
		}
		
		public static String commandLog(String pn, String cmd){
			return prefix("CommandLog") + pn + ": " + cmd;
		}
		
		public static String lootSpawned(int x, int y, int z){
			return prefix("Loot") + "A loot chest dropped at " + GOLD + "" + BOLD + x + " " + y + " " + z + RESET + YELLOW + "!";
		}
		
		public static String serverRestartingWarningSeconds(int seconds){
			String suffix;
			
			if (seconds == 1){
				suffix = "second";
			} else {
				suffix = "seconds";
			}
			
			return prefix() + "The server will restart in " + seconds + " " + suffix + "!";
		}
		
		public static String serverRestartingWarningMinutes(int minutes){
			String suffix;
			if (minutes == 1){
				suffix = "minute";
			} else {
				suffix = "minutes";
			}
			
			return prefix() + "The server will restart in " + minutes + " " + suffix + "!";
		}
		
		public static String someoneVotedRestart(String playerName, int totalVotes){
			return prefix() + playerName + " voted for a restart. Vote for a restart using /voterestart. Total votes: " + totalVotes;
		}
		
		public static String vote(String playerName, int points){
			return prefix() + playerName + " has voted and got " + points + " points! Vote at " + Var.VOTE_URL;
		}
		
		public static class Guilds {
			
			public static String inviteToGuild(Guild guild, UPlayer player){
				return prefix("Guilds") + player.getName() + " has invited you to join " + guild.getName() + ". Type " + AQUA + "/guild accept" + YELLOW + " to accept";
			}
			
			public static String joinedGuild(Guild guild){
				return prefix("Guilds") + "You have joined " + guild.getName();
			}
			
		}
		
		public static class JoinQuit {
			
			public static String playerJoin(String pn){
				return DARK_AQUA + "" + BOLD + pn + RESET + AQUA + " has joined";
			}	
			
			public static String quit(String pn){
				return DARK_AQUA + "" + BOLD + pn + RESET + AQUA + " has left";
			}
			
		}
		
		public static class Friends {
			
			public static String friendAdded(String pn){
				return prefix("Friends") + pn + " has been added to your friends list";
			}
			
			public static String friendRemoved(String pn){
				return prefix("Friends") + pn + " has been removed from your friends list";
			}
			
		}
		
		public static class Quests {
			
			public static String questCompleted(String quest, int xp){
				return prefix() + "You have completed quest " + BOLD + quest + RESET + YELLOW + " and got " + xp + " XP!";
			}
			
			public static String questCompleted(String quest, int xp, int lifeCrystals){
				return prefix() + "You have completed quest " + BOLD + quest + RESET + YELLOW + " and got " + xp + " XP and " + lifeCrystals + " Life Crystals!";
			}
			
			public static String npcNotFound(String name){
				return prefix("NPC") + RED + "No dialogue could be found for an npc with name " + name + RED + ", please report this error.";
			}	
			
			public static String npcMsg(String npc, String message){
				//return prefix(npc) + message;
				return DARK_AQUA + "" + BOLD + npc + ": " + RESET + "" + AQUA + message;
			}
			
		}
		
		public static class Commands {
			
			public static String userNotFound(String user){
				return prefix() + RED + "No additional information was found for user " + user;
			}
			
			public static String nowAfk(String name){
				return prefix() + YELLOW + name + " is now AFK";
			}
			
			public static String noLongerAfk(String name){
				return prefix() + YELLOW + name + " is no longer AFK";
			}
			
			public static class Mute {
				
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
				
			}
			
		}
	}
	
}
