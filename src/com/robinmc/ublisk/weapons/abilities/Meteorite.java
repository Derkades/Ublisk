package com.robinmc.ublisk.weapons.abilities;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;

import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.Var;
import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.Ublisk;
import com.robinmc.ublisk.utils.Ublisk.Explosion;

public class Meteorite extends Ability {

	private int damage;
	
	public Meteorite(int damage) {
		super(4, 0); // TODO Min level
		this.damage = damage;
	}

	@Override
	public void run(final UPlayer player) {
		Block clickedBlock = player.getTargetBlock(50);
		if (clickedBlock.getType().equals(Material.AIR)){
			return;
		}
		
		final Location clickedLocation = clickedBlock.getLocation();
		
		new BukkitRunnable() {

			//Location loc = player.getLocation();

			//double t = 12;
			
			double y = 100;

			public void run() {
				
				if (y <= 2.5){
					Ublisk.createFakeExplosion(clickedLocation, damage, 4, true, Explosion.BLAST_SMALL, Explosion.FIRE, Explosion.SMOKE);
					this.cancel();
					return;
				}
				
				if (y > 50){
					y -= 5;
				} else {
					y -= 2.5;
				}
				
				Location location = new Location(Var.WORLD, clickedLocation.getX(), clickedLocation.getY() + y, clickedLocation.getZ());
				Ublisk.spawnParticle(Particle.FLAME, location, 5, 0, 1, 0, 0);
				
				/*if (location.equals(clickedLocation)){
					Ublisk.createFakeExplosion(location, damage, 4, true, Explosion.BLAST_SMALL, Explosion.FIRE, Explosion.SMOKE);
					this.cancel();
				}*/
				/*t = t + 0.6;

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
					Ublisk.createFakeExplosion(loc.getBlock().getRelative(BlockFace.DOWN).getLocation(), damage, 4, true, Explosion.BLAST_SMALL, Explosion.FIRE, Explosion.SMOKE);
					
					loc.setY(loc.getY() - 1);
				}

				loc.subtract(x, y, z);*/
			}
		}.runTaskTimer(Main.getInstance(), 0, 1);
	}

}
