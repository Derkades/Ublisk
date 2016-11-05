package com.robinmc.ublisk.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.connorlinfoot.actionbarapi.ActionBarAPI;
import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.Var;
import com.robinmc.ublisk.mob.Mob;
import com.robinmc.ublisk.utils.exception.MobInfoMissingException;
import com.robinmc.ublisk.utils.exception.MobNotFoundException;
import com.robinmc.ublisk.utils.exception.UnknownAreaException;
import com.robinmc.ublisk.utils.logging.LogLevel;
import com.robinmc.ublisk.utils.logging.Logger;

public class Exp {
	
	public static boolean DOUBLE_XP_ACTIVE = false;
	public static int DOUBLE_XP_TIME = Var.DOUBLE_XP_TIME;
	public static boolean DOUBLE_XP_COOLDOWN = false;
	public static boolean DOUBLE_XP_BAR_ACTIVE = false;
	
	/**
	 * Set a player's experience points
	 * @param Player
	 * @param Number of experience points
	 */
	public static void set(OfflinePlayer player, int n){
		DataFile.XP.set("xp." + player.getUniqueId(), n);
		
		if (player.isOnline()){
			Player online = (Player) player;
			refresh(online);
		}
	}
	
	/**
	 * Adds experience points
	 * @param player
	 * @param Number of experience points
	 */
	public static void add(Player player, int i){
		set(player, get(player) + i);
		refresh(player);
	}
	
	/**
	 * Gets a player's XP as stored in file
	 * @param Player
	 * @return XP (not level!)
	 */
	public static int get(OfflinePlayer player){
		if (DataFile.XP.isSet("xp." + player.getUniqueId())){
			return DataFile.XP.getInteger("xp." + player.getUniqueId());
		} else { //If XP is not yet set in file set it to 0
			set(player, 0);
			
			if (player.isOnline()){
				Player online = (Player) player;
				refresh(online);
			}
			
			return 0;
		}
	}
	
	/**
	 * Get a player's level
	 * @param A player
	 * @return Player's level
	 */
	public static int getLevel(Player player){
		return player.getLevel();
	}
	
	/**
	 * Gives the player the amount of XP that is rewarded when the specified mob is killed
	 * @param Player
	 * @param Mob type
	 * @throws MobNotFoundException If the entity specified could not be associated with a Mob.
	 * @throws UnknownAreaException If the entity specified is not in an area
	 * @throws MobInfoMissingException  
	 */
	public static void giveMobExp(Player player, Entity entity) throws MobNotFoundException {
		Mob mob = Mob.getMob(entity);
		String name = mob.getName();
		int xp = mob.getXP();
		
		if (DOUBLE_XP_ACTIVE){ //If double XP is active
			ActionBarAPI.sendActionBar(player, ChatColor.GOLD + "You have killed a " + name + " and got " + xp * 2 + " XP", 3*10);
			Exp.add(player, xp * 2);
			Logger.log(LogLevel.INFO, "XP", "Given " + player.getName() + " " + xp * 2 + " for killing a " + name);
		} else {
			ActionBarAPI.sendActionBar(player, ChatColor.GREEN + "You have killed a " + name + " and got " + xp + " XP", 3*10);
			Exp.add(player, xp);
			Logger.log(LogLevel.INFO, "XP", "Given " + player.getName() + " " + xp + " for killing a " + name);
		}
	}
	
	/**
	 * Refreshes a player's xp from file to XP bar.
	 * @param Player
	 */
	public static void refresh(Player player){
		int xp = Exp.get(player);
		player.setExp(0);
	    player.setLevel(0);
	    player.setTotalExperience(0);  
	    player.giveExp(Math.round(xp / Var.XP_DIVISION));
	}
	
	public static void levelUp(Player player){
		int level = UPlayer.get(player).getLevel();
		Bukkit.broadcastMessage("");
		Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "--------------------------------------------");
		Bukkit.broadcastMessage(ChatColor.AQUA + "" + ChatColor.BOLD + player.getName() + ChatColor.DARK_AQUA + "" + ChatColor.BOLD + " has reached level " + player.getLevel() + "!");
		if (!(level < 5)) Bukkit.broadcastMessage(ChatColor.BLUE + "To celebrate this double XP will be activated in 10 seconds, get ready!");
		Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "--------------------------------------------");
		
		if (!(level < 5)){
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable(){
				public void run(){
					DOUBLE_XP_ACTIVE = true;
				}
			}, 10*20);
		}	
	}

}
