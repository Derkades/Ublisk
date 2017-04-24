package com.robinmc.ublisk.weapons.abilities;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.scheduler.BukkitRunnable;

import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.Ublisk;
import com.robinmc.ublisk.utils.shapes.Direction;
import com.robinmc.ublisk.utils.shapes.Shapes;

public class StoneCastle extends Ability {
	
	public StoneCastle() {
		super(1, 0);
	}

	@Override
	public void run(final UPlayer player) {

		player.setFrozen(true);
		new BukkitRunnable() {

			double t = 0;
			Location loc = player.getLocation();
			
			
			public void run() {
				t = t + 1;
				List<Location> particleLocations = Shapes.generateCircle(Direction.HORIZONTAL, loc.add(0, 1, 0), 30, 2.0);
				for (Location particleLocation : particleLocations){
				    Ublisk.spawnParticle(Particle.DRAGON_BREATH, particleLocation, 1, 0, 0, 0, 0.1); //Gebruik nu de variable 'particleLocation' als locatie
				}

				if(t > 60){
					player.setFrozen(false);
					this.cancel();
					
				}
			}
			
		}.runTaskTimer(Main.getInstance(), 0, 1);
	}
}