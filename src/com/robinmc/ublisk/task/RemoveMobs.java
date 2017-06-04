package com.robinmc.ublisk.task;

import org.bukkit.scheduler.BukkitRunnable;

import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.Message;
import com.robinmc.ublisk.mob.Mobs;
import com.robinmc.ublisk.utils.Ublisk;

public class RemoveMobs extends BukkitRunnable {

	@Override
	public void run(){
		Ublisk.broadcastPrefixedMessage("Clearing all mobs and items in 30 seconds!");
		new BukkitRunnable(){
			public void run(){
				Ublisk.broadcastPrefixedMessage("Clearing all mobs and items in 5 seconds!");
				new BukkitRunnable(){
					public void run(){
						Mobs.clearMobs();
						Ublisk.broadcastMessage(Message.ENTITIES_REMOVED);
					}
				}.runTaskLater(Main.getInstance(), 5*20);
			}
		}.runTaskLater(Main.getInstance(), 25*20);
	}

}
