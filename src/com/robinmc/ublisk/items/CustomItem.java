package com.robinmc.ublisk.items;

import org.bukkit.Material;

import com.robinmc.ublisk.utils.inventory.Item;

import net.minecraft.server.v1_11_R1.NBTTagString;

public class CustomItem extends Item {
	
	public CustomItem(String identifier){
		super(Material.BONE);
		
		this.setNBTValue("UbliskItemIdentifier", new NBTTagString(identifier));
	}

}
