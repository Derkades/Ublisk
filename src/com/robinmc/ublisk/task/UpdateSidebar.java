package com.robinmc.ublisk.task;

import org.bukkit.scheduler.BukkitRunnable;

import com.robinmc.ublisk.Scoreboard;
import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.Ublisk;

public class UpdateSidebar extends BukkitRunnable {

	@Override
	public void run() {
		for (UPlayer player : Ublisk.getOnlinePlayers()){
			Scoreboard.getScoreboard(player).showTo(player.getPlayer());
		}
	}

}
