package com.robinmc.ublisk.utils.mob;

import org.bukkit.entity.EntityType;

@Deprecated
public class MobInfo {
	
	private EntityType type;
	private int level;
	private int xp;
	private String name;
	private double health;
	
	public MobInfo(EntityType type, int level, double health, int xp, String name){
		this.type = type;
		this.level = level;
		this.name = name;
		this.xp = xp;
		this.health = health;
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
	
	public String getName(){
		return name;
	}

}
