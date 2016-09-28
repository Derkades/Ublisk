package com.robinmc.ublisk.task;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.connorlinfoot.actionbarapi.ActionBarAPI;
import com.robinmc.ublisk.enums.Tip;

public class RandomTips extends BukkitRunnable {

	@Override
	public void run(){
		for (Player player : Bukkit.getOnlinePlayers()){
			//player.sendMessage(Tip.getRandom());
			ActionBarAPI.sendActionBar(player, Tip.getRandom(), 100);
		}
	}

}
