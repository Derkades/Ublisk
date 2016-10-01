package com.robinmc.ublisk.utils.mob.type;

import org.bukkit.entity.EntityType;

import com.robinmc.ublisk.utils.mob.MobCode;

public class Chicken implements MobType {

	@Override
	public EntityType getEntityType() {
		return EntityType.CHICKEN;
	}

	@Override
	public MobCode getCode() {
		return null;
	}
	
}
