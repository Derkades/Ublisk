package xyz.derkades.ublisk.mob.mobs.zombie.villager;

import org.bukkit.Material;
import org.bukkit.entity.Villager.Profession;

import xyz.derkades.ublisk.mob.GoldDrop;
import xyz.derkades.ublisk.mob.MobDrop;
import xyz.derkades.ublisk.mob.Radius;
import xyz.derkades.ublisk.utils.inventory.Item;

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
