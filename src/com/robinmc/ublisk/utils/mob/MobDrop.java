package com.robinmc.ublisk.utils.mob;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import com.robinmc.ublisk.utils.java.Random;
import com.robinmc.ublisk.utils.logging.LogLevel;
import com.robinmc.ublisk.utils.logging.Logger;
import com.robinmc.ublisk.utils.variable.Var;

public class MobDrop {
	
	private ItemStack stack;
	private int percentage;
	
	public MobDrop(ItemStack item, int percentage){
		this.stack = item;
		this.percentage = percentage;
	}
	
	public void drop(Location loc){
		double d = percentage / 100.0; // 100% - 1.0 | 50% - 0.5
		Logger.log(LogLevel.DEBUG, "Mob", "Percentage / 100 = " + d);
		boolean drop = Random.getRandomDouble() <= d;
		Logger.log(LogLevel.DEBUG, "Mob", "Random: " + drop);
		if (drop){
			org.bukkit.entity.Item item = Var.WORLD.dropItemNaturally(loc, stack);
			item.setPickupDelay(10);
		}
	}
	
	public ItemStack getItemStack(){
		return stack;
	}

}