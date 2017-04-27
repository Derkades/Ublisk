package com.robinmc.ublisk.mob.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;

import com.robinmc.ublisk.database.PlayerInfo;
import com.robinmc.ublisk.mob.MobDrop;
import com.robinmc.ublisk.mob.v2.Mob;
import com.robinmc.ublisk.mob.v2.Mobs;
import com.robinmc.ublisk.utils.DoubleXP;
import com.robinmc.ublisk.utils.Logger;
import com.robinmc.ublisk.utils.Logger.LogLevel;
import com.robinmc.ublisk.utils.UPlayer;

public class EntityDeath implements Listener {
	
	@EventHandler
	public void entityDeath(EntityDeathEvent event){
		//Never let the entity drop any XP on death
		event.setDroppedExp(0);
		
		LivingEntity entity = event.getEntity();
		
		if (entity.getType() == EntityType.PLAYER){
			return;
		}
		
		if (entity.getLastDamageCause().getCause() != DamageCause.ENTITY_ATTACK){
			return;
		}
		
		if (!Mobs.getEntityTypes().contains(entity.getType())){
			return;
		}
		
		//Now we know that the entity has been killed by the player, so it is safe to call getKiller()
		UPlayer player = new UPlayer(entity.getKiller());
		
		//Track player kills for statistics
		player.tracker(PlayerInfo.MOB_KILLS);
		
		//Get mob from entity UUID
		Mob mob = Mobs.SPAWNED_MOBS.get(entity.getUniqueId());
		
		//Drop gold and items
		if (mob.getMobDrops().length > 0){
			for (MobDrop drop : mob.getMobDrops()) drop.drop(entity.getLocation());
		}
		for (MobDrop drop : mob.getGoldDrop().getMobDrops()) drop.drop(entity.getLocation());
		
		String name = mob.getName();
		String color;
		int xp;
		
		if (DoubleXP.isActive()){
			xp = mob.getXP() * 2;
			color = ChatColor.GOLD.toString();
		} else {
			xp = mob.getXP();
			color = ChatColor.GREEN.toString();
		}
		
		
		player.sendActionBarMessage(color + "You have killed a " + name + " and got " + xp * 2 + " XP");
		player.addXP(xp);
		Logger.log(LogLevel.INFO, "XP", "Given " + player.getName() + " " + xp + " for killing a " + name);
	}

}
