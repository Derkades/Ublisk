package com.robinmc.ublisk.task;

import org.bukkit.scheduler.BukkitRunnable;

import com.robinmc.ublisk.HashMaps;
import com.robinmc.ublisk.utils.Exp;
import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.Ublisk;

public class PlayerLevelUp extends BukkitRunnable {

	@Override
	public void run(){
		for (UPlayer player : Ublisk.getOnlinePlayers()){
			int current = player.getLevel();
			int last = HashMaps.PREVIOUS_LEVEL.get(player);
			if (current > last){
				Exp.levelUp(player);
			}
			HashMaps.PREVIOUS_LEVEL.put(player.getUniqueId(), current);
		}
	}

}
