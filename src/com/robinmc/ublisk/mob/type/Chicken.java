package com.robinmc.ublisk.mob.type;

import org.bukkit.entity.EntityType;

import com.robinmc.ublisk.mob.MobCode;
import com.robinmc.ublisk.mob.MobType;

@Deprecated
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
