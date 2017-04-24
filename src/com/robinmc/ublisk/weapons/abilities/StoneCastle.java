package com.robinmc.ublisk.weapons.abilities;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.Ublisk;
import com.robinmc.ublisk.utils.shapes.Direction;
import com.robinmc.ublisk.utils.shapes.Shapes;

import net.md_5.bungee.api.ChatColor;

public class StoneCastle extends Ability {
	
	public StoneCastle() {
		super(1, 0);
	}

	@Override
	public void run(final UPlayer player) {
		if (!player.onGround()){
			player.sendMessage(ChatColor.RED + "You must be on a solid block to use this ability.");
			return;
		}
		
		player.setFrozen(true);
		player.givePotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 7*20, 3);
		
		new BukkitRunnable() {

			double t = 0;
			final Location[] circleLocations = {
												player.getLocation().add(0, 0.5, 0),
												player.getLocation().add(0, 1.5, 0)
												};
			
			public void run() {		
				t = t + 1;
				for (Location circleLocation : circleLocations){
					List<Location> particleLocations = Shapes.generateCircle(Direction.HORIZONTAL, circleLocation, 30, 2.0);
					for (Location particleLocation : particleLocations){
						Ublisk.spawnParticle(Particle.EXPLOSION_NORMAL, particleLocation, 1, 0, 0, 0, 0.1); //Gebruik nu de variable 'particleLocation' als locatie
					}
				}
					
				if(t > 7*20){
					player.setFrozen(false);
					this.cancel();
					
				}
			}
			
		}.runTaskTimer(Main.getInstance(), 0, 1);
	}
}