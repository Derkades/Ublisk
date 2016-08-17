package com.robinmc.ublisk.task;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.utils.enums.Tip;
import com.robinmc.ublisk.utils.scheduler.Task;

public class RandomTips implements Task {

	@Override
	public void task(Main plugin) {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable(){
			public void run(){
				for (Player player : Bukkit.getOnlinePlayers()){
					player.sendMessage(Tip.getRandom());
				}
			}
		}, 20*20, 5*60*20);
	}

}
