package com.robinmc.ublisk.mob.v2.mobs.zombie;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

import com.robinmc.ublisk.mob.MobCode;
import com.robinmc.ublisk.mob.v2.Mob;

public abstract class Zombie extends Mob {
	
	@Override
	public EntityType getEntityType(){
		return EntityType.ZOMBIE;
	}
	
	@Override
	public MobCode getMobCode(){
		return new MobCode(){

			@Override
			public void mobCode(LivingEntity entity) {
				org.bukkit.entity.Zombie zombie = (org.bukkit.entity.Zombie) entity; // We can safely cast, because this code will only run on zombies
				
				//Clear equipment
				EntityEquipment equipment = zombie.getEquipment();
				ItemStack air = new ItemStack(Material.AIR);				
				equipment.setBoots(air);
				equipment.setLeggings(air);
				equipment.setChestplate(air);
				equipment.setHelmet(air);
				equipment.setItemInMainHand(air);
				equipment.setItemInOffHand(air);
			}
			
		};
	}

}
