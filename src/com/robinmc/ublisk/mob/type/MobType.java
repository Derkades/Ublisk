package com.robinmc.ublisk.mob.type;

import org.bukkit.entity.EntityType;

import com.robinmc.ublisk.mob.MobCode;

public interface MobType {
	
	public EntityType getEntityType();
	
	public MobCode getCode();

}
