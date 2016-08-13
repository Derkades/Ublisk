package com.robinmc.ublisk.utils.weapon;

import java.util.Arrays;
import java.util.List;

import com.robinmc.ublisk.utils.inventory.NBT;

public class Weapon {
	
	private WeaponType type;
	private NBT nbt;
	private String name;
	private List<String> lore;
	
	public Weapon(WeaponType type, NBT nbt, String name, String... lore){
		this.type = type;
		this.nbt = nbt;
		this.name = name;
		this.lore = Arrays.asList(lore);
	}
	
	public WeaponType getType(){
		return type;
	}
	
	public NBT getNBT(){
		return nbt;
	}
	
	public String getName(){
		return name;
	}
	
	public List<String> getLore(){
		return lore;
	}

}
