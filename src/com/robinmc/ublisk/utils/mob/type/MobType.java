package com.robinmc.ublisk.utils.mob.type;

import org.bukkit.entity.EntityType;

import com.robinmc.ublisk.mob.MobCode;

public interface MobType {
	
	public EntityType getEntityType();
	
	public MobCode getCode();

}
