package com.robinmc.ublisk.mob.type;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Villager.Profession;

import com.robinmc.ublisk.mob.MobCode;
import com.robinmc.ublisk.mob.MobType;

public class ZombieVillager implements MobType {

	private Profession profession = null;
	
	@Override
	public EntityType getEntityType() {
		return EntityType.ZOMBIE_VILLAGER;
	}

	@Override
	public MobCode getCode() {
		return new MobCode(){

			@Override
			public void mobCode(LivingEntity entity) {
				org.bukkit.entity.ZombieVillager zombie = (org.bukkit.entity.ZombieVillager) entity;
				if (profession != null) zombie.setVillagerProfession(profession);
			}
			
		};
	}
	
	public ZombieVillager withProfession(Profession profession){
		this.profession = profession;
		return this;
	}

}
