package com.robinmc.ublisk.utils.enums;

import org.bukkit.entity.Entity;

import com.robinmc.ublisk.utils.exception.MobNotFoundException;

public enum Mob {
	
	CHICKEN(1, "Chicken"),
	ZOMBIFIED_MERCHANT(5, "Zombified Merchant"),
	SHEEP(1, "Sheep");
	
	private Integer xp;
	private String name;
	
	Mob(Integer xp, String name){
		this.xp = xp;
		this.name = name;
	}
	
	public int getExp(){
		return xp;
	}
	
	public String getName(){
		return name;
	}
	
	public boolean equals(Entity entity){
		return name.equals(entity.getName());
	}
	
	public static Mob getMob(Entity entity) throws MobNotFoundException{
		for (Mob mob : Mob.values()){
			if (mob.getName() == entity.getName()){
				return mob;
			}
		}
		
		throw new MobNotFoundException();
		
	}
}
