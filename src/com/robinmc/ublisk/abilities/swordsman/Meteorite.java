package com.robinmc.ublisk.abilities.swordsman;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftEntity;
import org.bukkit.entity.Creeper;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.Var;
import com.robinmc.ublisk.abilities.AbilityExecutor;
import com.robinmc.ublisk.utils.UPlayer;

import net.minecraft.server.v1_11_R1.Entity;
import net.minecraft.server.v1_11_R1.EntityLiving;
import net.minecraft.server.v1_11_R1.NBTTagCompound;

public class Meteorite extends AbilityExecutor {

	@Override
	public void doAbility(final UPlayer player) {
		new BukkitRunnable(){
			Location loc = player.getLocation();
			
			double t = 12;
			
			@SuppressWarnings("deprecation")
			public void run(){				
				t = t + 0.6;
				
                Vector direction = loc.getDirection().normalize();
                
				double x, y, z;
				
				double m;
	
				m = t / 3.14;
				
                x = direction.getX() * m;
                y = Math.sqrt( -1 * Math.pow(t-33, 3) );
                z = direction.getZ() * m;
                
                loc.add(x,y,z);
                
                System.out.println("Y: " + loc.getY());
                System.out.println("t: " + t);
                
                Var.WORLD.spigot().playEffect(loc, Effect.FLAME, 26, 0, 0, 1, 0, 0, 5, 100); // XXX New method https://www.spigotmc.org/threads/deprecation-of-world-spigot-playeffect-alternatives.194307
                
                if (loc.getY() < (player.getLocation().getY() + 1.5) ){
                    this.cancel();

                    Creeper creeper = Var.WORLD.spawn(loc, Creeper.class);
                    
                    creeper.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 1000000*20, 0, true));
                    
                    Entity nms = ((CraftEntity) creeper).getHandle();
                    NBTTagCompound nbt = new NBTTagCompound();
                    nms.c(nbt);
                    nbt.setInt("Fuse", 0);
                    EntityLiving living = (EntityLiving) nms;
                    living.a(nbt);
               }
                
               loc.subtract(x,y,z);
			}
		}.runTaskTimer(Main.getInstance(), 0, 1);
	}

	@Override
	public int getCooldown() {
		return 10;
	}

}
