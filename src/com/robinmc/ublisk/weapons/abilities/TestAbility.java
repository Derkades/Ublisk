package com.robinmc.ublisk.weapons.abilities;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.Ublisk;

public class TestAbility extends Ability {

	public TestAbility() {
		super(5, 0); // TODO Min level
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

				Block block = loc.getBlock();

				// Stop if the ability has been going for some time or if it has hit a block.
				if (t > 20 || block.getType() != Material.AIR) {
					this.cancel();
					Ublisk.createExplosion(loc, 2.0f, false);
				}

				loc.subtract(x, y, z);
			}
		}.runTaskTimer(Main.getInstance(), 0, 1);
	}

}
