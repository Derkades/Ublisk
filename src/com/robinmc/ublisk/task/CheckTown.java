package com.robinmc.ublisk.task;

import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

import com.robinmc.ublisk.Town;
import com.robinmc.ublisk.utils.Logger;
import com.robinmc.ublisk.utils.Logger.LogLevel;
import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.Ublisk;

public class CheckTown extends BukkitRunnable {

	@Override
	public void run(){
		for (UPlayer player: Ublisk.getOnlinePlayers()){
			Town town = player.getTown();
			Town lastTown = player.getLastTown();
			
			if (town == null){ //If the player is no longer in a town 
				player.sendSubTitle(ChatColor.GRAY + "You left " + lastTown.getName());
			} else if (!town.getName().equals(lastTown.getName())){ //If the player is in a different town
				player.sendSubTitle(ChatColor.GRAY + "You are now in " + town.getName());
				Logger.log(LogLevel.INFO, "Town", player.getName() + ": " + player.getTown().getName() + " -> "+ town.getName());
				player.setLastTown(town);
			}
		}
	}

}
