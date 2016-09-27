package com.robinmc.ublisk.abilities.swordsman;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftEntity;
import org.bukkit.entity.Creeper;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.abilities.AbilityExecutor;
import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.variable.Var;

import net.minecraft.server.v1_10_R1.Entity;
import net.minecraft.server.v1_10_R1.EntityLiving;
import net.minecraft.server.v1_10_R1.NBTTagCompound;

public class TestAbility implements AbilityExecutor {

	@Override
	public void doAbility(final UPlayer player) {
		new BukkitRunnable(){
			double t = 0;
			Location loc = player.getLocation();
			
			public void run(){
				t = t + 0.8;
                Vector direction = loc.getDirection().normalize();
                double x = direction.getX() * t;
                double y = direction.getY() * t + 1.5;
                double z = direction.getZ() * t;
                loc.add(x,y,z);
                Var.WORLD.spigot().playEffect(loc, Effect.FIREWORKS_SPARK, 3, 0, 0, 0, 0, 0, 1, 50);
                loc.subtract(x,y,z);
                
                if (t > 20){
                    this.cancel();
                    loc.add(x,y,z);

                    Creeper creeper = Var.WORLD.spawn(loc, Creeper.class);
                    
                    creeper.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 1000000*20, 0, true));
                    
                    Entity nms = ((CraftEntity) creeper).getHandle();
                    NBTTagCompound nbt = new NBTTagCompound();
                    nms.c(nbt);
                    nbt.setInt("Fuse", 0);
                    EntityLiving living = (EntityLiving) nms;
                    living.a(nbt);
                }
			}
		}.runTaskTimer(Main.getInstance(), 0, 1);
	}

}
