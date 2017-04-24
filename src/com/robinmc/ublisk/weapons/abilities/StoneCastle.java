package com.robinmc.ublisk.weapons.abilities;

import org.bukkit.Particle;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.Location;

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
		/*new BukkitRunnable() {

			double t = 0;
			org.bukkit.Location loc = player.getLocation();
			
			public void run() {
				t = t + 1;
				Ublisk.spawnParticle(Particle.DRAGON_BREATH, Shapes.generateCircle(Direction.HORIZONTAL, loc.add(0, 1, 0), 30, 2.0), 1, 0, 0, 0, 0.1);
				if(t < 60){
					this.cancel();
				}
			}
			
		}.runTaskTimer(Main.getInstance(), 0, 1);*/
	}
}