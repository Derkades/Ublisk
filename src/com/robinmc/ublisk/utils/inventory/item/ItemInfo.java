package com.robinmc.ublisk.utils.inventory.item;

import java.util.Arrays;
import java.util.List;

public class ItemInfo {
	
	private String name;
	private List<String> lore;
	
	/**
	 * Provides info on an item
	 * @param name The name of an item
	 * @param lore The lore of an item. Multiple lines are supported
	 */
	public ItemInfo(String name, String... lore){
		this.name = name;
		this.lore = Arrays.asList(lore);
	}
	
	public ItemInfo(String name, List<String> lore) {
		this.name = name;
		this.lore = lore;
	}

	public String getName(){
		return name;
	}
	
	public List<String> getLore(){
		return lore;
	}

}
