package com.robinmc.ublisk.abilities.swordsman;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.abilities.Ability;
import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.Ublisk;

public class TestAbility extends Ability {

	public TestAbility() {
		super(5, 0); //TODO Min level
	}

	@Override
	public void run(final UPlayer player) {
		new BukkitRunnable() {

			double t = 0;
			Location loc = player.getLocation();

			public void run() {
				t = t + 0.8;
				Vector direction = loc.getDirection().normalize();
				double x = direction.getX() * t;
				double y = direction.getY() * t + 1.5;
				double z = direction.getZ() * t;
				loc.add(x, y, z);
				Ublisk.spawnParticle(Particle.FIREWORKS_SPARK, loc, 1, 0, 0, 0, 0);
				loc.subtract(x, y, z);

				if (t > 20) {
					this.cancel();
					loc.add(x, y, z);

					Ublisk.createExplosion(loc, 1.5f, false);
				}
			}
		}.runTaskTimer(Main.getInstance(), 0, 1);
	}

}
