package com.robinmc.ublisk.utils.inventory;

import org.bukkit.Material;

import net.minecraft.server.v1_12_R1.NBTTagString;

public class CustomItem extends Item {
	
	public CustomItem(String identifier){
		super(Material.BONE);
		
		this.setNBTValue("UbliskItemIdentifier", new NBTTagString(identifier));
	}

}
