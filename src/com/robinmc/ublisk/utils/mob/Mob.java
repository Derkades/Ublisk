package com.robinmc.ublisk.utils.mob;


import static net.md_5.bungee.api.ChatColor.DARK_AQUA;
import static net.md_5.bungee.api.ChatColor.DARK_GRAY;
import static net.md_5.bungee.api.ChatColor.DARK_GREEN;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

import com.robinmc.ublisk.utils.exception.MobNotFoundException;
import com.robinmc.ublisk.utils.logging.LogLevel;
import com.robinmc.ublisk.utils.logging.Logger;
import com.robinmc.ublisk.utils.variable.Var;

public enum Mob {
	
	/*
	 * Spawn location x
	 * Spawn location y
	 * Diameter for the previously mentioned x and y
	 * Entity type
	 * Level
	 * Health (1.5 is one and a half heart)
	 * XP given when killed
	 * Max spawns within area
	 * Name
	 * Spawn rate (4 means 1 every 4 seconds)
	 * 
	 * NOTE: Every mob must have at least a unique name or level.
	 */
	
	//TEST_MOB(63, -56, 5, EntityType.CHICKEN, 2, 5, 4, 10, "Test", 2),
	INTRODUCTION_SHEEP(27, -50, 5, 
			EntityType.SHEEP, 1, 1.5, 2, 4, "Sheep", 10, GoldDrop.LEVEL2,
			new MobDrop(new ItemStack(Material.WOOL), 70)),
	CHICKEN(66, -40, 60, 
			EntityType.CHICKEN, 1, 0.5, 1, 20, "Chicken", 5, GoldDrop.LEVEL1,
			new MobDrop(new ItemStack(Material.FEATHER), 30));
	
	private int x;
	private int z;
	private int diameter;
	private EntityType type;
	private int level;
	private double health;
	private int xp;
	private int max;
	private String name;
	private double rate;
	private GoldDrop gold;
	private MobDrop[] drops;

	Mob(int x, int z, int diameter, EntityType type, int level, double health, int xp, int max, String name, double spawn, GoldDrop gold, MobDrop... drops){
		this.x = x;
		this.z = z;
		this.diameter = diameter;
		this.type = type;
		this.level = level;
		this.health = health;
		this.xp = xp;
		this.max = max;
		this.name = name;
		this.rate = spawn;
		this.gold = gold;
		this.drops = drops;
	}
	
	public int getX(){
		return x;
	}
	
	public int getZ(){
		return z;
	}
	
	public int getRadius(){
		return diameter / 2;
	}
	
	public EntityType getEntityType(){
		return type;
	}
	
	public int getLevel(){
		return level;
	}
	
	public double getHealth(){
		return health;
	}
	
	public int getXP(){
		return xp;
	}
	
	public int getMax(){
		return max;
	}
	
	public String getName(){
		return name;
	}
	
	public double getSpawnRate(){
		return rate;
	}
	
	public GoldDrop getGoldDrop(){
		return gold;
	}
	
	public MobDrop[] getDrops(){
		return drops;
	}
	
	public boolean hasReachedMax(){
		int count = 0;
		
		for (LivingEntity entity : Var.WORLD.getLivingEntities()){
			try {
				if (getMob(entity) == this) count++;
			} catch (MobNotFoundException e) {}
		}
		
		Logger.log(LogLevel.DEBUG, count + "");
		
		return count >= getMax();
	}

	public static void removeMobs(){
		for (Entity entity: Var.WORLD.getEntities()){
			EntityType type = entity.getType();
			if (type == EntityType.CHICKEN ||
					type == EntityType.SHEEP ||
					type == EntityType.DROPPED_ITEM ||
					type == EntityType.EXPERIENCE_ORB){
				entity.remove();
			}
		}
	}
	
	public static Mob getMob(Entity entity) throws MobNotFoundException {
		for (Mob mob : Mob.values()){
			String name = DARK_AQUA + mob.getName() + DARK_GRAY + " [" + DARK_GREEN + mob.getLevel() + DARK_GRAY + "]";
			if (entity.getName().equals(name)){
				return mob;
			}
		}
		throw new MobNotFoundException();
	}
	
	public static boolean containsEntityType(EntityType type){
		for (Mob mob : Mob.values()){
			if (mob.getEntityType() == type){
				return true;
			}
		}
		return false;
	}
	
	public static void startMobSpawning(){
		SpawnMob.spawnMobs();
	}

}
