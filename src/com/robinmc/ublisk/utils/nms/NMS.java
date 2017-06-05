package com.robinmc.ublisk.utils.nms;

import org.bukkit.entity.Creature;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public interface NMS {
	
	public void setTarget(Creature creature, LivingEntity target);
	
	public void setTargetToClosestPlayer(Creature creature, Player player);

}
