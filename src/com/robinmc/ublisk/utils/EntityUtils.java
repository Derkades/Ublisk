package com.robinmc.ublisk.utils;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import com.robinmc.ublisk.utils.variable.Var;

public class EntityUtils {
	
	public static void removeMobs(){
		for (Entity entity: Var.world().getEntities()){
			EntityType type = entity.getType();
			if (type == EntityType.CHICKEN ||
					type == EntityType.SHEEP ||
					type == EntityType.DROPPED_ITEM ||
					type == EntityType.EXPERIENCE_ORB){
				entity.remove();
			}
		}
	}

}
