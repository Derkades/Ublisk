package com.robinmc.ublisk.utils;

import java.lang.reflect.Method;

import org.bukkit.craftbukkit.v1_11_R1.entity.CraftEntity;
import org.bukkit.entity.Entity;

import net.minecraft.server.v1_11_R1.NBTTagCompound;

public class NBTUtils {
	
	public static NBTTagCompound getEntityNBT(Entity entity){
		NBTTagCompound compound = new NBTTagCompound();
		CraftEntity craftEntity = (CraftEntity) entity;
		net.minecraft.server.v1_11_R1.Entity nmsEntity = craftEntity.getHandle();
		nmsEntity.c(compound);
		return compound;
	}
	
	public static void applyNBTToEntity(Entity entity, NBTTagCompound compound){
	    CraftEntity craft = ((CraftEntity) entity);
	    net.minecraft.server.v1_11_R1.Entity nms = craft.getHandle();
	    Class<?> entityClass = nms.getClass();
	    Method[] methods = entityClass.getMethods();
	    for (Method method : methods) {
	        if ((method.getName() == "a")
	                && (method.getParameterTypes().length == 1)
	                && (method.getParameterTypes()[0] == NBTTagCompound.class)) {
	            try {
	                method.setAccessible(true);
	                method.invoke(nms, compound);
	            } catch (Exception ex) {
	                ex.printStackTrace();
	            }
	        }
	    }
	    craft.setHandle(nms);
	}

}
