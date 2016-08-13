package com.robinmc.ublisk.utils.inventory;

import java.util.Arrays;
import java.util.List;

public class NBT {
	
	private List<NBTTag> nbt;
	
	public NBT(NBTTag... nbt){
		this.nbt = Arrays.asList(nbt);
	}
	
	public List<NBTTag> getTags(){
		return nbt;
	}

}
