package com.robinmc.ublisk.task;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.connorlinfoot.actionbarapi.ActionBarAPI;
import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.enums.Tip;
import com.robinmc.ublisk.utils.scheduler.Task;

public class RandomTips implements Task {

	@Override
	public void task(Main plugin) {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable(){
			public void run(){
				for (Player player : Bukkit.getOnlinePlayers()){
					//player.sendMessage(Tip.getRandom());
					ActionBarAPI.sendActionBar(player, Tip.getRandom(), 100);
				}
			}
		}, 20*20, 5*60*20);
	}

}
