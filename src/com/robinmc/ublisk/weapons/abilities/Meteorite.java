package com.robinmc.ublisk.weapons.abilities;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.Ublisk;

public class Meteorite extends Ability {

	public Meteorite() {
		super(4, 0); // TODO Min level
	}

	@Override
	public void run(final UPlayer player) {
		new BukkitRunnable() {

			Location loc = player.getLocation();

			double t = 12;

			public void run() {
				t = t + 0.6;

				Vector direction = loc.getDirection().normalize();

				double x, y, z;

				double m;

				m = t / 3.14;

				x = direction.getX() * m;
				y = Math.sqrt(-1 * Math.pow(t - 33, 3));
				z = direction.getZ() * m;

				loc.add(x, y, z);

				System.out.println("Y: " + loc.getY());
				System.out.println("t: " + t);

				Ublisk.spawnParticle(Particle.FLAME, loc, 5, 0, 1, 0, 0);

				if (loc.getY() < (player.getLocation().getY() + 1.5)) {
					this.cancel();

					Ublisk.createExplosion(loc, 2.0f, true);
				}

				loc.subtract(x, y, z);
			}
		}.runTaskTimer(Main.getInstance(), 0, 1);
	}

}
