package com.robinmc.ublisk.task;

import org.bukkit.scheduler.BukkitRunnable;

import com.robinmc.ublisk.Tip;
import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.Ublisk;

public class RandomTips extends BukkitRunnable {

	@Override
	public void run(){
		for (UPlayer player : Ublisk.getOnlinePlayers()){
			//ActionBarAPI.sendActionBar(player, Tip.getRandom(), 100);
			player.sendActionBarMessage(Tip.getRandomTip());
		}
	}

}
