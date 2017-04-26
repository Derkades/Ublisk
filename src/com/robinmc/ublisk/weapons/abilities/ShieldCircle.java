package com.robinmc.ublisk.weapons.abilities;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.Ublisk;

public class ShieldCircle extends Ability {

	public ShieldCircle() {
		super(1, 0);
	}

	@Override
	public void run(final UPlayer player) throws Exception {
		new BukkitRunnable() {

			double t = Math.PI / 4;
			Location loc = player.getLocation();

			public void run() {
				t = t + 0.1 * Math.PI;
				for (double a = 0; a <= 2 * Math.PI; a = a + Math.PI / 32) {
					double x = t * Math.sin(a);
					double y = 2 * Math.exp(-0.1 * t) * Math.sin(t) + 1.5;
					double z = t * Math.sin(a);
					loc.add(x, y + 1.5, z);
					Ublisk.spawnParticle(Particle.REDSTONE, loc, 255, 140, 0, 0);

		                   for (final Entity e : loc.getChunk().getEntities()){ //fix e = entity
					        	if (e.getLocation().distance(loc) < 1.0){
					        		if (e.getType() == EntityType.PLAYER){
					        			player.givePotionEffect(PotionEffectType.SATURATION, 10*20, 1);
					        			new BukkitRunnable(){ 
					        				Location locp = e.getLocation();
					        				double b = 0;
					        				public void run(){
					        					b += Math.PI/10;
					        					for (double c = 0; c <= 2*Math.PI; c += Math.PI/40){
					        						double r = 1.5;
					        						double x = r*Math.cos(c)*Math.sin(b);
					        						double y = r*Math.cos(b) + 1;
					        						double z = r*Math.sin(c)*Math.sin(b);
					        						locp.add(x, y, z);
					                                Ublisk.spawnParticle(Particle.REDSTONE, locp, 255, 140, 0, 0);
					                                locp.subtract(x, y, z);
					                                
					                                if (b > Math.PI){
					                                	this.cancel();
					                                }
					        					}
					        				}
					        			}.runTaskTimer(Main.getInstance(), 0, 1); 
					        		}
					        	}
					        }
				}
				if (t > 20) {
					this.cancel();
				}
			}

		}.runTaskTimer(Main.getInstance(), 0, 1);
	}
}