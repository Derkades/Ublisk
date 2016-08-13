package com.robinmc.ublisk.utils.inventory;

import net.minecraft.server.v1_10_R1.NBTTagCompound;

public class NBTTag {
	
	private NBTTagCompound nbt = new NBTTagCompound();
	
	public NBTTag(String arg0, String arg1){
		nbt.setString(arg0, arg1);
	}
	
	public NBTTag(String arg0, int arg1){
		nbt.setInt(arg0, arg1);
	}
	
	public NBTTag(String arg0, double arg1){
		nbt.setDouble(arg0, arg1);
	}
	
	public NBTTagCompound getCompound(){
		return nbt;
	}

}
