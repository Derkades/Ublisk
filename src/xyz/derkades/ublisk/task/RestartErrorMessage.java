package xyz.derkades.ublisk.task;

import org.bukkit.scheduler.BukkitRunnable;

import net.md_5.bungee.api.ChatColor;
import xyz.derkades.ublisk.utils.Ublisk;

public class RestartErrorMessage extends BukkitRunnable {

	@Override
	public void run() {
		if (Ublisk.RESTART_ERROR)
			Ublisk.broadcastMessage(ChatColor.RED + "" + ChatColor.BOLD + "An error has occured due to unsafe reloading. Please restart the server as soon as possible.");
	}

}
