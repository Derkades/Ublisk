package com.robinmc.ublisk.weapons.abilities;

import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.utils.UPlayer;

public class ShieldCircle extends Ability {

	public ShieldCircle() {
		super(1, 0);
	}
	
	@Override
	public void run(final UPlayer player) {
        new BukkitRunnable(){
            double t = Math.PI/4;
            Location loc = player.getLocation();
            public void run(){
                    t = t + 0.1*Math.PI;
                    for (double a = 0; a <= 2*Math.PI; a = a + Math.PI/32){
                            double x = t*Math.sin(a);
                            double y = 2*Math.exp(-0.1*t) * Math.sin(t) + 1.5;
                            double z = t*Math.sin(a);
                            loc.add(x,y,z);
                            //Ublisk.spawnParticle(particle, location, count, offsetX, offsetY, offsetZ, speed);
                            loc.subtract(x,y,z);
                           
                            a = a + Math.PI/64;
                           
                            x = t*Math.cos(a);
                            y = 2*Math.exp(-0.1*t) * Math.sin(t) + 1.5;
                            z = t*Math.sin(a);
                            loc.add(x,y,z);
                           // ParticleEffect.WITCH_MAGIC.display(loc,0,0,0,0,1);
                            loc.subtract(x,y,z);
                    }
                    if (t > 20){
                            this.cancel();
                    }
            }
                                   
    }.runTaskTimer(Main.getInstance(), 0, 1);
}
}