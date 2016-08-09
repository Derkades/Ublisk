package com.robinmc.ublisk.utils.mob;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.EntityType;

import com.robinmc.ublisk.utils.Area;
import com.robinmc.ublisk.utils.exception.MobInfoMissingException;

public enum MobArea {
	
	//Area: x < ..., x > ..., z < ..., z > ...
	//MobInfo: (EntityType) entity type, (int) level, (double) health, (int) xp, (String) name
	INTRODUCTION(new Area(100, 22, 20, -90), 
			new MobInfo(EntityType.SHEEP, 1, 1.5, 5, "Sheep"), 
			new MobInfo(EntityType.CHICKEN, 1, 0.5, 2, "Chicken"));
	
	private Area area;
	private List<MobInfo> info;
	
	MobArea(Area area, MobInfo... info){
		this.area = area;
		
		List<MobInfo> list = new ArrayList<MobInfo>();
		for (MobInfo infoList : info) list.add(infoList);
		this.info = list;
	}
	
	public Area getArea(){
		return area;
	}
	
	public List<MobInfo> getMobInfo(){
		return info;
	}
	
	public static MobInfo getMobInfo(MobArea area, EntityType type) throws MobInfoMissingException {
		for (MobInfo info : area.getMobInfo()){
			if (type == info.getEntityType()){
				return info;
			}
		}
		throw new MobInfoMissingException();
	}

}
