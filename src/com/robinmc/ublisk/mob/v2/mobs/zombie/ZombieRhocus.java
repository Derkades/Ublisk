package com.robinmc.ublisk.mob.v2.mobs.zombie;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.robinmc.ublisk.mob.GoldDrop;
import com.robinmc.ublisk.mob.MobDrop;
import com.robinmc.ublisk.mob.Radius;

public class ZombieRhocus extends Zombie {

	@Override
	public boolean isBaby(){
		return false;
	}
	
	@Override
	public Radius getArea() {
		return new Radius(280, 90, 318, 60);
	}

	@Override
	public int getLevel() {
		return 15;
	}

	@Override
	public double getHealth() {
		return 15;
	}

	@Override
	public int getMinimumXP() {
		return 5;
	}

	@Override
	public int getMaximumXP() {
		return 15;
	}

	@Override
	public int getMaxSpawns() {
		return 50;
	}

	@Override
	public String getName() {
		return "Rhocus Zombie";
	}

	@Override
	public double getSpawnRate() {
		return 5;
	}

	@Override
	public GoldDrop getGoldDrop() {
		return GoldDrop.LEVEL3;
	}

	@Override
	public MobDrop[] getMobDrops() {
		return new MobDrop[]{
				new MobDrop(new ItemStack(Material.ROTTEN_FLESH), 0, 2),
				new MobDrop(new ItemStack(Material.ROTTEN_FLESH), 2, 3)
		};
	}

}
