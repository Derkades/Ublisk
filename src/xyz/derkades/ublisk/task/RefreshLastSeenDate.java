package xyz.derkades.ublisk.task;

import org.bukkit.scheduler.BukkitRunnable;

import xyz.derkades.ublisk.utils.UPlayer;
import xyz.derkades.ublisk.utils.Ublisk;

public class RefreshLastSeenDate extends BukkitRunnable {

	@Override
	public void run() {
		for (UPlayer player : Ublisk.getOnlinePlayers()){
			player.refreshLastSeenDate();
		}
	}

}
