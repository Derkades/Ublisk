package com.robinmc.ublisk.utils.mob;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

import com.robinmc.ublisk.utils.Area;
import com.robinmc.ublisk.utils.exception.MobNotFoundException;
import com.robinmc.ublisk.utils.exception.UnknownAreaException;
import com.robinmc.ublisk.utils.variable.Var;

public enum Mob {
	
	CHICKEN(EntityType.CHICKEN, MobArea.INTRODUCTION),
	SHEEP(EntityType.SHEEP, MobArea.INTRODUCTION);
	
	private EntityType type;
	private List<MobArea> area;
	
	Mob(EntityType type, MobArea... mobArea){
		this.type = type;
		List<MobArea> list = new ArrayList<MobArea>();
		for (MobArea singleArea : mobArea){
			list.add(singleArea);
		}
		area = list;
	}
	
	public EntityType getType(){
		return type;
	}
	
	public List<MobArea> getAreas(){
		return area;
	}
	
	public static boolean belongsInArea(LivingEntity entity) throws MobNotFoundException, UnknownAreaException{
		return Mob.getMob(entity).getAreas().contains(Mob.getArea(entity));
	}
	
	public static Mob getMob(Entity entity) throws MobNotFoundException {
		
		for (Mob mob : Mob.values()){
			if (mob.getType() == entity.getType()){
				return mob;
			}
		}
		
		throw new MobNotFoundException();
	}
	
	private static List<EntityType> getAllEntityTypes(){
		List<EntityType> list = new ArrayList<EntityType>();
		for (Mob mob : Mob.values()){
			EntityType type = mob.getType();
			if (!list.contains(type))
				list.add(type);
		}
		return list;
	}
	
	public static boolean containsEntity(Entity entity){
		return getAllEntityTypes().contains(entity.getType());
	}
	
	public static MobArea getArea(Entity entity) throws UnknownAreaException {
		for (MobArea mobArea : MobArea.values()){
			Area area = mobArea.getArea();
			int x = entity.getLocation().getBlockX();
			int z = entity.getLocation().getBlockZ();
			if (	x < area.lessX() &&
					x > area.moreX() &&
					z < area.lessZ() &&
					z > area.moreZ()){
				return mobArea;
			}
		}
		throw new UnknownAreaException();
	}
	
	public static void removeMobs(){
		for (Entity entity: Var.world.getEntities()){
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
