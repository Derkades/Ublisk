package com.robinmc.ublisk.weapons.abilities;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.block.BlockFace;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.Var;
import com.robinmc.ublisk.utils.Logger;
import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.Ublisk;
import com.robinmc.ublisk.utils.Logger.LogLevel;
import com.robinmc.ublisk.utils.Ublisk.Explosion;

public class Meteorite extends Ability {

	private int damage;
	
	public Meteorite(int damage) {
		super(4, 0); // TODO Min level
		this.damage = damage;
	}

	@Override
	public void run(final UPlayer player) {
		new BukkitRunnable() {

			Location loc = player.getLocation();

			double t = 12;

			public void run() {
				t = t + 0.6;

				Vector direction = loc.getDirection().normalize();

				double x, unsquared, y, z;

				double m;

				m = t / 3.14;

				x = direction.getX() * m;
				unsquared = -1 * Math.pow(t - 33, 3);
				if (unsquared <= 0){
					Logger.log(LogLevel.WARNING, "Abilities", "Meteorite ability: y unsquared is less than 0 (" + unsquared + ")");
					this.cancel();
					return;
				}
				y = Math.sqrt(unsquared);
				z = direction.getZ() * m;

				loc.add(x, y, z);

				System.out.println("Y: " + loc.getY());
				System.out.println("t: " + t);

				Ublisk.spawnParticle(Particle.FLAME, loc, 5, 0, 1, 0, 0);

				if (loc.getY() < (player.getLocation().getY() + 1.5)) {
					this.cancel();
					
					Location lower = new Location(Var.WORLD, loc.getX(), loc.getY(), loc.getZ());
					lower.setY(loc.getY() - 0.6);
					Ublisk.createFakeExplosion(loc.getBlock().getRelative(BlockFace.DOWN).getLocation(), damage, 4, Explosion.BLAST_SMALL, Explosion.FIRE, Explosion.SMOKE);
					
					loc.setY(loc.getY() - 1);
				}

				loc.subtract(x, y, z);
			}
		}.runTaskTimer(Main.getInstance(), 0, 1);
	}

}
