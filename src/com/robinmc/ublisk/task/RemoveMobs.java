package com.robinmc.ublisk.task;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.Message;
import com.robinmc.ublisk.mob.Mob;
import com.robinmc.ublisk.utils.Ublisk;

public class RemoveMobs extends BukkitRunnable {

	@Override
	public void run(){
		Bukkit.broadcastMessage(Message.Complicated.removeMobsWarning(30));
		new BukkitRunnable(){
			public void run(){
				Bukkit.broadcastMessage(Message.Complicated.removeMobsWarning(5));
				new BukkitRunnable(){
					public void run(){
						Mob.removeMobs();
						Ublisk.broadcastMessage(Message.ENTITIES_REMOVED);
					}
				}.runTaskLater(Main.getInstance(), 5*20);
			}
		}.runTaskLater(Main.getInstance(), 25*20);
	}

}
