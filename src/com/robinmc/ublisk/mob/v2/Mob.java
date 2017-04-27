package com.robinmc.ublisk.mob.v2;

import static net.md_5.bungee.api.ChatColor.DARK_AQUA;
import static net.md_5.bungee.api.ChatColor.DARK_GRAY;
import static net.md_5.bungee.api.ChatColor.DARK_GREEN;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

import com.robinmc.ublisk.Var;
import com.robinmc.ublisk.mob.GoldDrop;
import com.robinmc.ublisk.mob.MobCode;
import com.robinmc.ublisk.mob.MobDrop;
import com.robinmc.ublisk.mob.Radius;
import com.robinmc.ublisk.utils.java.Random;

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
	
	public int getXP(){
		return Random.getRandomInteger(this.getMinimumXP(), this.getMaximumXP());
	}
	
	public boolean hasReachedSpawnLimit(){
		return this.getAllLivingEntitiesOfType().size() >= this.getMaxSpawns();
	}
	
	public List<LivingEntity> getAllLivingEntitiesOfType(){
		List<LivingEntity> list = new ArrayList<LivingEntity>();
		for (LivingEntity entity : Var.WORLD.getLivingEntities()){
			if (this.equals(entity)){
				list.add(entity);
			}
		}
		return list;
	}
	
	@Override
	public boolean equals(Object other){
		if (other instanceof Mob){
			Mob otherMob = (Mob) other;
			return otherMob.getName().equals(this.getName()) &&
					otherMob.getMinimumXP() == this.getMinimumXP() &&
					otherMob.getMaximumXP() == this.getMaximumXP() &&
					otherMob.getLevel() == this.getLevel();
		} else {
			return false;
		}
	}
	
	@Override
	public String toString(){
		return this.getName();
	}

}
