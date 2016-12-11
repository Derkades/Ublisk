package com.robinmc.ublisk.task;

import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

import com.robinmc.ublisk.Town;
import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.Ublisk;
import com.robinmc.ublisk.utils.exception.NotInATownException;
import com.robinmc.ublisk.utils.logging.LogLevel;
import com.robinmc.ublisk.utils.logging.Logger;

public class CheckTown extends BukkitRunnable {

	@Override
	public void run(){
		for (UPlayer player: Ublisk.getOnlinePlayers()){
			Town town;
			
			try {
				town = player.getCurrentTown();
			} catch (NotInATownException e) {
				continue;
			}
			
			if (town.getName() != player.getTown().getName()){
				player.sendSubTitle(ChatColor.GRAY + "You are now in " + town.getName());
				Logger.log(LogLevel.INFO, "Town", player.getName() + " is now in " + town.getName() + " and got there from " + player.getTown());
				player.setLastTown(town);
			}
		}
	}

}
