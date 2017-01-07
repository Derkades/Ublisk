package com.robinmc.ublisk.task;

import org.bukkit.scheduler.BukkitRunnable;

import com.robinmc.ublisk.utils.Ublisk;

import net.md_5.bungee.api.ChatColor;

public class RestartErrorMessage extends BukkitRunnable {

	@Override
	public void run() {
		Ublisk.broadcastMessage(ChatColor.RED + "" + ChatColor.BOLD + "An error has occured due to unsafe reloading. Please restart the server as soon as possible.");
	}

}
