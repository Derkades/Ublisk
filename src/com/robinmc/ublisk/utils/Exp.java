package com.robinmc.ublisk.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import com.robinmc.ublisk.DataFile;
import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.Var;

public class Exp {

	/**
	 * Set a player's experience points
	 * 
	 * @param Player
	 * @param Number of experience points
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
	 * @param player Offline player
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
	 * @param player
	 * @return Player's level
	 */
	public static int getLevel(Player player) {
		return player.getLevel();
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
		
		if (player.getGuild() != null){
			Guild guild = player.getGuild();
			guild.addPoints(1);
		}
	}

}
