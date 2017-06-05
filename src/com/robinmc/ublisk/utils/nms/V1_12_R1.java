package com.robinmc.ublisk.utils.nms;

import org.bukkit.craftbukkit.v1_12_R1.entity.CraftCreature;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftLivingEntity;
import org.bukkit.entity.Creature;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;

import net.minecraft.server.v1_12_R1.EntityCreature;
import net.minecraft.server.v1_12_R1.EntityLiving;

public class V1_12_R1 implements NMS {

	@Override
	public void setTarget(Creature creature, LivingEntity target) {
		CraftCreature craftCreature = (CraftCreature) creature;
	    EntityCreature nms = craftCreature.getHandle();
	    if (target == null) {
	        nms.setGoalTarget(null, null, false);
	    } else {
	        nms.setGoalTarget(((CraftLivingEntity)target).getHandle(), null, false);
	    }
	}

	@Override
	public void setTargetToClosestPlayer(Creature creature, Player player) {
		CraftLivingEntity craftLiving = (CraftLivingEntity) player;
		EntityLiving entityLiving = craftLiving.getHandle();
		((CraftCreature) creature).getHandle().setGoalTarget(entityLiving, TargetReason.CLOSEST_PLAYER, false);
	}

}
