package com.robinmc.ublisk.task;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.utils.Config;
import com.robinmc.ublisk.utils.Console;
import com.robinmc.ublisk.utils.Task;
import com.robinmc.ublisk.utils.enums.Town;

public class CheckTown implements Task {

	@Override
	public void task(Main plugin) {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable(){
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
								Console.sendMessage("[Towns] " + player.getName() + " is now in " + town.getName() + " and got there from " + Config.getString("last-town." + player.getUniqueId()));
								Config.set("last-town." + player.getUniqueId(), town.getName());
							}
						}
					}
				}
			}
		}, 0, 2*20);
	}

}
