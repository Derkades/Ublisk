package com.robinmc.ublisk.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.robinmc.ublisk.DataFile;
import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.Var;
import com.robinmc.ublisk.mob.Mob;
import com.robinmc.ublisk.mob.v2.Mobs;
import com.robinmc.ublisk.utils.Logger.LogLevel;
import com.robinmc.ublisk.utils.exception.MobNotFoundException;
import com.robinmc.ublisk.utils.exception.UnknownAreaException;

public class Exp {

	/**
	 * Set a player's experience points
	 * 
	 * @param Player
	 * @param Number
	 *            of experience points
	 */
	public static void set(OfflinePlayer player, int n) {
		DataFile.XP.getConfig().set("xp." + player.getUniqueId(), n);

		if (player.isOnline()) {
			Player online = (Player) player;
			refresh(online);
		}
	}

	/**
	 * Gets a player's XP as stored in file
	 * 
	 * @param Player
	 * @return XP (not level!)
	 */
	public static int get(OfflinePlayer player) {
		if (DataFile.XP.getConfig().isSet("xp." + player.getUniqueId())) {
			return DataFile.XP.getConfig().getInt("xp." + player.getUniqueId());
		} else { //If XP is not yet set in file set it to 0
			set(player, 0);

			if (player.isOnline()) {
				Player online = (Player) player;
				refresh(online);
			}

			return 0;
		}
	}

	/**
	 * Get a player's level
	 * 
	 * @param A
	 *            player
	 * @return Player's level
	 */
	public static int getLevel(Player player) {
		return player.getLevel();
	}

	/**
	 * Gives the player the amount of XP that is rewarded when the specified mob
	 * is killed
	 * 
	 * @param Player
	 * @param Mob
	 *            type
	 * @throws MobNotFoundException
	 *             If the entity specified could not be associated with a Mob.
	 * @throws UnknownAreaException
	 *             If the entity specified is not in an area
	 * @throws MobInfoMissingException
	 */
	public static void giveMobExp(UPlayer player, Entity entity) throws MobNotFoundException {
		com.robinmc.ublisk.mob.v2.Mob mob = Mobs.SPAWNED_MOBS.get(entity.getUniqueId());
		String name = mob.getName();
		int xp = mob.getXP();

		if (DoubleXP.isActive()) { //If double XP is active
			player.sendActionBarMessage(ChatColor.GOLD + "You have killed a " + name + " and got " + xp * 2 + " XP");
			player.addXP(xp * 2);
			Logger.log(LogLevel.INFO, "XP", "Given " + player.getName() + " " + xp * 2 + " for killing a " + name);
		} else {
			player.sendActionBarMessage(ChatColor.GREEN + "You have killed a " + name + " and got " + xp + " XP");
			player.addXP(xp);
			Logger.log(LogLevel.INFO, "XP", "Given " + player.getName() + " " + xp + " for killing a " + name);
		}
	}

	/**
	 * Refreshes a player's xp from file to XP bar.
	 * 
	 * @param Player
	 */
	public static void refresh(Player player) {
		int xp = Exp.get(player);
		player.setExp(0);
		player.setLevel(0);
		player.setTotalExperience(0);
		player.giveExp(Math.round(xp / Var.XP_DIVISION));
	}

	public static void levelUp(final UPlayer player) {
		int level = player.getLevel();
		Bukkit.broadcastMessage("");
		Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "--------------------------------------------");
		Bukkit.broadcastMessage(ChatColor.AQUA + "" + ChatColor.BOLD + player.getName() + ChatColor.DARK_AQUA + ""
				+ ChatColor.BOLD + " has reached level " + player.getLevel() + "!");
		if (!(level < 5))
			Bukkit.broadcastMessage(
					ChatColor.BLUE + "To celebrate this double XP will be activated in 10 seconds, get ready!");
		Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "--------------------------------------------");

		if (!(level < 5)) {
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {

				public void run() {
					DoubleXP.startDoubleXP(player);
				}
			}, 10 * 20);
		}
	}

}
