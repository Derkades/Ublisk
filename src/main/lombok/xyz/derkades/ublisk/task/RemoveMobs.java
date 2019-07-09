package xyz.derkades.ublisk.task;

import org.bukkit.scheduler.BukkitRunnable;

import xyz.derkades.ublisk.Main;
import xyz.derkades.ublisk.Message;
import xyz.derkades.ublisk.mob.Mobs;
import xyz.derkades.ublisk.utils.Ublisk;

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
