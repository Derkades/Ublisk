package com.robinmc.ublisk.task;

import org.bukkit.scheduler.BukkitRunnable;

import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.Ublisk;

public class SetMaxHealth extends BukkitRunnable {

	@Override
	public void run(){
		for (UPlayer player : Ublisk.getOnlinePlayers()){
			if (player.getMaxHealth() != player.getCorrectMaxHealth())
				player.setMaxHealth(player.getCorrectMaxHealth());
		}
	}

}
