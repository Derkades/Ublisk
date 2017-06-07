package xyz.derkades.ublisk.task;

import org.bukkit.scheduler.BukkitRunnable;

import xyz.derkades.ublisk.Loot;
import xyz.derkades.ublisk.utils.java.Random;

public class SpawnRandomLoot extends BukkitRunnable {

	@Override
	public void run() {
		int random = Random.getRandomInteger(0, 5);
		if (random == 0){
			Loot.getRandomLoot().spawn();
		}
	}

}
