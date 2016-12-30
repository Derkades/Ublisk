package com.robinmc.ublisk;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.robinmc.ublisk.commands.Command;
import com.robinmc.ublisk.listeners.Listeners;
import com.robinmc.ublisk.mob.Mob;
import com.robinmc.ublisk.task.Task;
import com.robinmc.ublisk.utils.DoubleXP;
import com.robinmc.ublisk.utils.Logger;
import com.robinmc.ublisk.utils.PacketListener;

public class Main extends JavaPlugin {

	public static Main instance;

	@Override
	public void onEnable() {
		instance = this;

		HashMaps.resetAllPlayers();

		new BukkitRunnable() {
			public void run() {
				Listeners.register();
			}
		}.runTaskLater(this, 5);

		new BukkitRunnable() {
			public void run() {
				Command.registerAll();
			}
		}.runTaskLater(this, 10);

		new BukkitRunnable() {
			public void run() {
				Mob.startMobSpawning();
			}
		}.runTaskLater(this, 15);

		new BukkitRunnable() {
			public void run() {
				for (Task task : Task.values())
					task.start();
			}
		}.runTaskLater(this, 20);

		new BukkitRunnable() {
			public void run() {
				DoubleXP.startDoubleXPPacketListener();
			}
		}.runTaskLater(this, 25);
		
		new BukkitRunnable() {
			public void run() {
				Logger.startSiteLogger();
			}
		}.runTaskLater(this, 30);

	}

	@Override
	public void onDisable() {
		instance = null;

		PacketListener.closeAllOpenSockets();
	}

	public static Main getInstance() {
		return instance;
	}

}
