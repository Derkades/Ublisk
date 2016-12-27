package com.robinmc.ublisk.abilities.swordsman;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftEntity;
import org.bukkit.entity.Creeper;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.Var;
import com.robinmc.ublisk.abilities.Ability;
import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.Ublisk;

import net.minecraft.server.v1_11_R1.Entity;
import net.minecraft.server.v1_11_R1.EntityLiving;
import net.minecraft.server.v1_11_R1.NBTTagCompound;

public class Meteorite extends Ability {

	public Meteorite() {
		super(4, 0); // TODO Min level
	}

	@Override
	public void run(final UPlayer player) {
		new BukkitRunnable() {
			Location loc = player.getLocation();

			double t = 12;
			
			public void run() {
				t = t + 0.6;

				Vector direction = loc.getDirection().normalize();

				double x, y, z;

				double m;

				m = t / 3.14;

				x = direction.getX() * m;
				y = Math.sqrt(-1 * Math.pow(t - 33, 3));
				z = direction.getZ() * m;

				loc.add(x, y, z);

				System.out.println("Y: " + loc.getY());
				System.out.println("t: " + t);

				Ublisk.spawnParticle(Particle.FLAME, loc, 5, 0, 1, 0, 0);

				if (loc.getY() < (player.getLocation().getY() + 1.5)) {
					this.cancel();

					Creeper creeper = Var.WORLD.spawn(loc, Creeper.class);

					creeper.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 1000000 * 20, 0, true));

					Entity nms = ((CraftEntity) creeper).getHandle();
					NBTTagCompound nbt = new NBTTagCompound();
					nms.c(nbt);
					nbt.setInt("Fuse", 0);
					EntityLiving living = (EntityLiving) nms;
					living.a(nbt);
				}

				loc.subtract(x, y, z);
			}
		}.runTaskTimer(Main.getInstance(), 0, 1);
	}

}
