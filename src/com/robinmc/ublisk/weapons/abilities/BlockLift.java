package com.robinmc.ublisk.weapons.abilities;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.FallingBlock;
import org.bukkit.material.MaterialData;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.Var;
import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.Ublisk;
import com.robinmc.ublisk.utils.Ublisk.Explosion;

public class BlockLift extends Ability {

	private static final int EXPLOSION_RADIUS = 4;
	
	private int damage;
	
	public BlockLift(int damage) {
		super(1, 0); // TODO Min level TODO Mana
		this.damage = damage;
	}

	@Override
	public void run(final UPlayer player) throws Exception {
		new BukkitRunnable() {

			double t = 0;
			Location loc = player.getLocation();

			@SuppressWarnings("deprecation")
			public void run() {
				t = t + 0.8;
				Vector direction = loc.getDirection().normalize();
				double x = direction.getX() * t;
				double y = direction.getY() * t + 1.5;
				double z = direction.getZ() * t;
				loc.add(x, y, z);
				Ublisk.spawnParticle(Particle.FIREWORKS_SPARK, loc, 1, 0, 0, 0, 0);

				final Location blockCenterLocation = new Location(Var.WORLD, loc.getBlockX() + 0.5, loc.getBlockY(),
						loc.getBlockZ() + 0.5);
				// I can't use loc.getBlock(), because then the falling block will summon at the wrong location
				final Block block = blockCenterLocation.getBlock();

				// Stop if the ability has been going for some time or if it has hit a block.
				if (t > 20 || block.getType() != Material.AIR) {
					this.cancel();
					Block below = block.getRelative(BlockFace.DOWN);
					Block above = block.getRelative(BlockFace.UP);
					Block aboveAbove = above.getRelative(BlockFace.UP);
					// If lower block is solid and the two blocks above are air
					if (below.getType().isSolid() && above.getType() == Material.AIR
							&& aboveAbove.getType() == Material.AIR) {
						Ublisk.spawnParticle(Particle.SMOKE_LARGE, blockCenterLocation, 15, 0, 0, 0, 0.365);
						final FallingBlock fall = block.getWorld().spawnFallingBlock(blockCenterLocation,
								new MaterialData(block.getType(), block.getData()));
						final Vector velocity = fall.getVelocity();
						velocity.setY(velocity.getY() + 1.0);
						fall.setVelocity(velocity);
						block.setType(Material.AIR);
						Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {

							@Override
							public void run() {
								Ublisk.createFakeExplosion(blockCenterLocation, damage, EXPLOSION_RADIUS, Explosion.FLAMES);
							}
						}, 2 * 20);
					} else {
						player.sendMessage("Can't lift block"); // TODO Fancy message
					}
				}

				loc.subtract(x, y, z);
			}
		}.runTaskTimer(Main.getInstance(), 0, 1);
	}

}
