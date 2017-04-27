package com.robinmc.ublisk.mob.mobs.zombie.villager;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Villager.Profession;

import com.robinmc.ublisk.mob.MobCode;
import com.robinmc.ublisk.mob.mobs.zombie.Zombie;

public abstract class ZombieVillager extends Zombie {
	
	@Override
	public EntityType getEntityType() {
		return EntityType.ZOMBIE_VILLAGER;
	}

	@Override
	public MobCode getMobCode() {
		return new MobCode(){

			@Override
			public void mobCode(LivingEntity entity) {
				entity.getEquipment().clear();
				org.bukkit.entity.ZombieVillager zombie = (org.bukkit.entity.ZombieVillager) entity;
				zombie.setBaby(isBaby());
				if (getProfession() != null) zombie.setVillagerProfession(getProfession());
			}
			
		};
	}
	
	public abstract Profession getProfession();
	
	public abstract boolean isBaby();

}
