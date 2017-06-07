package xyz.derkades.ublisk.task;

import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

import xyz.derkades.ublisk.Var;

public class ClearWeather extends BukkitRunnable {

	@Override
	public void run(){
		World world = Var.WORLD;
		world.setThundering(false);
		world.setStorm(false);
	}
}
