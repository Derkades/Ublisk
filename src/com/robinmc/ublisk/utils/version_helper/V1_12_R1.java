package com.robinmc.ublisk.utils.version_helper;

import org.bukkit.craftbukkit.v1_12_R1.entity.CraftCreature;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Creature;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import net.minecraft.server.v1_12_R1.ChatMessageType;
import net.minecraft.server.v1_12_R1.EntityCreature;
import net.minecraft.server.v1_12_R1.EntityLiving;
import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_12_R1.PacketPlayOutChat;

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

	@Override
	public void sendActionBarMessage(Player player, String message) {
		BaseComponent[] components = TextComponent.fromLegacyText(message); //Convert string with color codes to BaseComponent array
		String json = ComponentSerializer.toString(components); //Convert to JSON string
		IChatBaseComponent base = ChatSerializer.a(json);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutChat(base, ChatMessageType.GAME_INFO));
	}

	@Override
	public void sendTitle(Player player, String title, String subtitle) {
		((CraftPlayer) player).sendTitle(title, subtitle);
	}

}
