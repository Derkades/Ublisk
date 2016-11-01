package com.robinmc.ublisk.task;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.robinmc.ublisk.Town;
import com.robinmc.ublisk.utils.Config;
import com.robinmc.ublisk.utils.logging.LogLevel;
import com.robinmc.ublisk.utils.logging.Logger;

public class CheckTown extends BukkitRunnable {

	@Override
	@SuppressWarnings("deprecation")
	public void run(){
		for (Player player: Bukkit.getOnlinePlayers()){
			for (Town town: Town.values()){
				Location loc = player.getLocation();
				if (	loc.getX() < town.lessX() &&
						loc.getX() > town.moreX() &&
						loc.getZ() < town.lessZ() &&
						loc.getZ() > town.moreZ()){
					if (!(town.getName() == Config.getString("last-town." + player.getUniqueId()))){
						player.sendTitle("", ChatColor.GRAY + "You are now in " + town.getName());
						Logger.log(LogLevel.INFO, "Town", player.getName() + " is now in " + town.getName() + " and got there from " + Config.getString("last-town." + player.getUniqueId()));
						Config.set("last-town." + player.getUniqueId(), town.getName());
					}
				}
			}
		}
	}

}
