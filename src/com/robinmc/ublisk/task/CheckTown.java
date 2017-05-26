package com.robinmc.ublisk.task;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

import com.robinmc.ublisk.Town;
import com.robinmc.ublisk.utils.Logger;
import com.robinmc.ublisk.utils.Logger.LogLevel;
import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.Ublisk;

public class CheckTown extends BukkitRunnable {

	private static final Set<UUID> SENT_LEFT_MESSAGE = new HashSet<>();

	@Override
	public void run(){
		for (UPlayer player: Ublisk.getOnlinePlayers()){
			Town town = player.getTown();
			Town lastTown = player.getLastTown();
			
			if (town == null && !SENT_LEFT_MESSAGE.contains(player.getUniqueId())){ //If the player is no longer in a town 
				player.sendSubTitle(ChatColor.GRAY + "You left " + lastTown.getName());
				SENT_LEFT_MESSAGE.add(player.getUniqueId());
				Logger.log(LogLevel.INFO, "Town", player.getName() + " left " + lastTown.getName());
			} else if (town != null && SENT_LEFT_MESSAGE.contains(player.getUniqueId())){ //As soon as the player enters a town and has got a left message
				player.sendSubTitle(ChatColor.GRAY + "You are now in " + town.getName());
				Logger.log(LogLevel.INFO, "Town", player.getName() + " is now in " + lastTown.getName());
				player.setLastTown(town);
				SENT_LEFT_MESSAGE.remove(player.getUniqueId());
			}
		}
	}

}
