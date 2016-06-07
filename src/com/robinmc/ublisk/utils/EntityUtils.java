package com.robinmc.ublisk.utils;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import com.robinmc.ublisk.Var;

public class EntityUtils {
	
	public static void removeMobs(){
		for (Entity entity: Var.world().getEntities()){
			if (entity.getType() == EntityType.CHICKEN ||
					entity.getType() == EntityType.SHEEP ||
					entity.getType() == EntityType.DROPPED_ITEM ||
					entity.getType() == EntityType.EXPERIENCE_ORB){
				entity.remove();
			}
		}
	}

}
