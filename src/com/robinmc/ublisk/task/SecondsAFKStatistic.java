package com.robinmc.ublisk.task;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.robinmc.ublisk.database.PlayerInfo;

public class SecondsAFKStatistic extends BukkitRunnable {

	@Override
	public void run() {
		for (Player player : Bukkit.getOnlinePlayers()){
			UUID uuid = player.getUniqueId();
			PlayerInfo.SECONDS_AFK.put(uuid, PlayerInfo.SECONDS_AFK.get(uuid) + 1);
			PlayerInfo.SECONDS_NOT_AFK.put(uuid, PlayerInfo.SECONDS_NOT_AFK.get(uuid) + 1);
		}
	}

}
