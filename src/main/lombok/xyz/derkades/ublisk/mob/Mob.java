package xyz.derkades.ublisk.mob;

import static net.md_5.bungee.api.ChatColor.DARK_AQUA;
import static net.md_5.bungee.api.ChatColor.DARK_GRAY;
import static net.md_5.bungee.api.ChatColor.DARK_GREEN;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

import xyz.derkades.derkutils.Random;
import xyz.derkades.ublisk.Var;

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
			if (Mobs.SPAWNED_MOBS.containsKey(entity.getUniqueId())){
				Mob mob = Mobs.SPAWNED_MOBS.get(entity.getUniqueId());
				if (mob.getName().equals(this.getName()) &&
						mob.getHealth() == this.getHealth() &&
						mob.getMinimumXP() == this.getMinimumXP() &&
						mob.getMaximumXP() == this.getMaximumXP()){
					list.add(entity);
					
				}
			}
		}
		return list;
	}
	
	@Override
	public String toString(){
		return this.getName();
	}

}
