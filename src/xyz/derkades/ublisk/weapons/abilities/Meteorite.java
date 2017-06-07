package xyz.derkades.ublisk.weapons.abilities;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;

import xyz.derkades.ublisk.Main;
import xyz.derkades.ublisk.Var;
import xyz.derkades.ublisk.utils.UPlayer;
import xyz.derkades.ublisk.utils.Ublisk;
import xyz.derkades.ublisk.utils.Ublisk.Explosion;

public class Meteorite extends Ability {

	private int damage;
	
	public Meteorite(int damage) {
		super(4, 0); // TODO Min level
		this.damage = damage;
	}

	@Override
	public boolean run(final UPlayer player) {
		Block clickedBlock = player.getTargetBlock(50);
		if (clickedBlock.getType().equals(Material.AIR)){
			return false;
		}
		
		final Location clickedLocation = clickedBlock.getLocation();
		
		new BukkitRunnable() {
			
			double y = 100;

			public void run() {
				
				if (y <= 2.5){
					Ublisk.createFakeExplosion(clickedLocation, player, damage, 4, true, Explosion.BLAST_SMALL, Explosion.FIRE, Explosion.SMOKE);
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
			}
		}.runTaskTimer(Main.getInstance(), 0, 1);
		
		return true;
	}

}
