package xyz.derkades.ublisk.mob.mobs.zombie;

import org.bukkit.Material;

import xyz.derkades.ublisk.mob.GoldDrop;
import xyz.derkades.ublisk.mob.MobDrop;
import xyz.derkades.ublisk.mob.Radius;
import xyz.derkades.ublisk.utils.inventory.Item;

public class ZombieRhocus extends Zombie {

	@Override
	public boolean isBaby(){
		return false;
	}
	
	@Override
	public Radius getArea() {
		return new Radius(327, 80, 316, 100);
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
				new MobDrop(new Item(Material.ROTTEN_FLESH), 0, 2),
				new MobDrop(new Item(Material.ROTTEN_FLESH), 2, 3)
		};
	}

}
