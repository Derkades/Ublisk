package com.robinmc.ublisk.mob.v2;

import static net.md_5.bungee.api.ChatColor.DARK_AQUA;
import static net.md_5.bungee.api.ChatColor.DARK_GRAY;
import static net.md_5.bungee.api.ChatColor.DARK_GREEN;

import org.bukkit.entity.EntityType;

import com.robinmc.ublisk.mob.GoldDrop;
import com.robinmc.ublisk.mob.MobCode;
import com.robinmc.ublisk.mob.MobDrop;
import com.robinmc.ublisk.mob.Radius;

public abstract class Mob {
	
	public abstract Radius getArea();
	
	public abstract EntityType getEntityType();
	
	public abstract int getLevel();
	
	public abstract double getHealth();
	
	public abstract int getMinimumXP();
	
	public abstract int getMaximumXP();
	
	public abstract int getMaxSpawns();
	
	public abstract String getName();
	
	public abstract double getSpawnRate();
	
	public abstract GoldDrop getGoldDrop();
	
	public abstract MobDrop[] getMobDrops();
	
	public abstract MobCode getMobCode();
	
	public String getDisplayName(){
		return DARK_AQUA + this.getName() + DARK_GRAY + " [" + DARK_GREEN + this.getLevel() + DARK_GRAY + "]";
	}
	
	private static final Mob[] MOB_LIST = new Mob[]{};

}
