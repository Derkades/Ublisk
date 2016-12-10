package com.robinmc.ublisk.task;

import org.bukkit.scheduler.BukkitRunnable;

import com.robinmc.ublisk.Tip;
import com.robinmc.ublisk.utils.UPlayer;

public class RandomTips extends BukkitRunnable {

	@Override
	public void run(){
		for (UPlayer player : UPlayer.getOnlinePlayers()){
			//ActionBarAPI.sendActionBar(player, Tip.getRandom(), 100);
			player.sendActionBarMessage(Tip.getRandomTip());
		}
	}

}
