package com.robinmc.ublisk.utils;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.connorlinfoot.actionbarapi.ActionBarAPI;
import com.robinmc.ublisk.HashMaps;
import com.robinmc.ublisk.utils.exception.MobNotFoundException;
import com.robinmc.ublisk.utils.exception.UnknownAreaException;
import com.robinmc.ublisk.utils.mob.Mob;
import com.robinmc.ublisk.utils.mob.MobArea;
import com.robinmc.ublisk.utils.mob.MobInfo;
import com.robinmc.ublisk.utils.variable.Message;
import com.robinmc.ublisk.utils.variable.Var;

public class Exp {
	
	/**
	 * Set a player's experience points
	 * @param Player
	 * @param Number of experience points
	 */
	public static void set(OfflinePlayer player, int n){
		Config.set("xp." + player.getUniqueId(), n);
	}
	
	/**
	 * Adds experience points
	 * @param player
	 * @param Number of experience points
	 */
	public static void add(Player player, int n){
		Config.set("xp." + player.getUniqueId(), n + get(player));
		refresh(player);
	}
	
	/**
	 * Gets a player's XP as stored in configuration
	 * @param Player
	 * @return XP (not level!)
	 */
	public static int get(OfflinePlayer player){
		if (Config.getConfig().isSet("xp." + player.getUniqueId())){
			return Config.getInteger("xp." + player.getUniqueId());
		} else { //If XP is not yet set in config set it to 0
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
	 */
	public static void giveMobExp(Player player, Entity entity) throws MobNotFoundException, UnknownAreaException{
		if (!Mob.containsEntity(entity)){
			player.sendMessage(Message.ERROR_GENERAL.get());
			return;
		}
		
		MobArea area = Mob.getArea(entity);
		int xp = 0;
		String name = "error";
		for (MobInfo info : area.getMobInfo()){
			if (entity.getType() == info.getEntityType()){
				xp = info.getXP();
				name = info.getName();
			}
			
			throw new MobNotFoundException();
		}
		
		if (HashMaps.doublexp.get(HashMaps.placeHolder())){ //If double XP is active
			ActionBarAPI.sendActionBar(player, ChatColor.GOLD + "You have killed a " + name + " and got " + xp * 2 + " XP", 2*10);
			Exp.add(player, xp * 2);
			Console.sendMessage("[MobExp] Given " + player.getName() + " " + xp * 2 + " for killing a " + name);
		} else {
			ActionBarAPI.sendActionBar(player, ChatColor.GREEN + "You have killed a " + name + " and got " + xp + " XP", 2*10);
			Exp.add(player, xp);
			Console.sendMessage("[MobExp] Given " + player.getName() + " " + xp + " for killing a " + name);
		}	
		refresh(player);
	}
	
	/**
	 * Refreshes a player's xp from config to XP bar.
	 * @param Player
	 */
	public static void refresh(Player player){
		int xp = Exp.get(player);
		player.setExp(0);
	    player.setLevel(0);
	    player.setTotalExperience(0);  
	    player.giveExp(Math.round(xp / Var.xpDivision));
	}

}
