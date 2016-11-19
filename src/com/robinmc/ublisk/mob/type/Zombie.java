package com.robinmc.ublisk.mob.type;

import org.bukkit.craftbukkit.v1_11_R1.entity.CraftZombie;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

import com.robinmc.ublisk.mob.MobCode;
import com.robinmc.ublisk.mob.MobType;

import net.minecraft.server.v1_11_R1.EntityZombie;

public class Zombie implements MobType {
	
	private boolean isBaby = false;
	
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
				zombie.setBaby(isBaby);
			}
			
		};
	}
	
	public void setBaby(boolean isBaby){
		this.isBaby = isBaby;
	}

}
