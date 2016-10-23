package com.robinmc.ublisk.utils.mob.type;

import org.bukkit.DyeColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

import com.robinmc.ublisk.mob.MobCode;

public class Sheep implements MobType {

	private DyeColor color = DyeColor.WHITE;
	
	public void setColor(DyeColor color){
		this.color = color;
	}

	@Override
	public EntityType getEntityType() {
		return EntityType.SHEEP;
	}

	@Override
	public MobCode getCode() {
		return new MobCode(){

			@Override
			public void mobCode(LivingEntity entity) {
				org.bukkit.entity.Sheep sheep = (org.bukkit.entity.Sheep) entity;
				sheep.setColor(color);
			}
			
		};
	}

}
