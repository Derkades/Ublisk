package com.robinmc.ublisk.mob.mobs.zombie.villager;

import org.bukkit.Material;
import org.bukkit.entity.Villager.Profession;

import com.robinmc.ublisk.mob.GoldDrop;
import com.robinmc.ublisk.mob.MobDrop;
import com.robinmc.ublisk.mob.Radius;
import com.robinmc.ublisk.utils.inventory.Item;

public class RuinsDweller extends ZombieVillager {

	@Override
	public Profession getProfession() {
		return Profession.FARMER;
	}

	@Override
	public boolean isBaby() {
		return false;
	}

	@Override
	public Radius getArea() {
		return new Radius(221, -23, 20);
	}

	@Override
	public int getLevel() {
		return 4;
	}

	@Override
	public double getHealth() {
		return 10;
	}

	@Override
	public int getMinimumXP() {
		return 2;
	}

	@Override
	public int getMaximumXP() {
		return 7;
	}

	@Override
	public int getMaxSpawns() {
		return 7;
	}

	@Override
	public String getName() {
		return "Ruins Dweller";
	}

	@Override
	public double getSpawnRate() {
		return 10;
	}

	@Override
	public GoldDrop getGoldDrop() {
		return GoldDrop.LEVEL3;
	}

	@Override
	public MobDrop[] getMobDrops() {
		return new MobDrop[]{
				new MobDrop(new Item(Material.ROTTEN_FLESH), 0, 2)
		};
	}

}
