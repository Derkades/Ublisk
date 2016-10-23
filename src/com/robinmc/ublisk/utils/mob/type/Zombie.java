package com.robinmc.ublisk.utils.mob.type;

import org.bukkit.craftbukkit.v1_10_R1.entity.CraftZombie;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

import com.robinmc.ublisk.mob.MobCode;

import net.minecraft.server.v1_10_R1.EntityZombie;
import net.minecraft.server.v1_10_R1.EnumZombieType;

public enum Zombie implements MobType {
		
	NORMAL(EnumZombieType.NORMAL),
	HUSK(EnumZombieType.HUSK),
	VILLAGER_BROWN(EnumZombieType.VILLAGER_FARMER),
	VILLAGER_WHITE(EnumZombieType.VILLAGER_LIBRARIAN);
	
	// TODO All zombie villager types
	
	private boolean isBaby;
	
	private EnumZombieType type;
	
	Zombie(EnumZombieType type){
		this.type = type;
	}
	
	@Override
	public EntityType getEntityType(){
		return EntityType.ZOMBIE;
	}
	
	@Override
	public MobCode getCode(){
		return new MobCode(){

			@Override
			public void mobCode(LivingEntity entity) {
				CraftZombie craftZombie = (CraftZombie) entity;
				EntityZombie zombie = craftZombie.getHandle();
				zombie.setVillagerType(type);
				zombie.setBaby(isBaby);
			}
			
		};
	}
	
	public void setBaby(boolean isBaby){
		this.isBaby = isBaby;
	}

}
