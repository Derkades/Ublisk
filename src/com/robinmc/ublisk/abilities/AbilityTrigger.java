package com.robinmc.ublisk.abilities;

import org.bukkit.Material;

public class AbilityTrigger {
	
	private Material item;
	private TriggerType type;
	
	AbilityTrigger(Material item, TriggerType type){
		this.item = item;
		this.type = type;
	}
	
	public Material getItemType(){
		return item;
	}
	
	public TriggerType getTriggerType(){
		return type;
	}
	
	public enum TriggerType {
		
		RIGHT_CLICK,
		LEFT_CLICK;
		
	}

}
