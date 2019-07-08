package xyz.derkades.ublisk.utils.version_helper;

import java.lang.reflect.Field;

import org.bukkit.block.Chest;
import org.bukkit.craftbukkit.v1_14_R1.block.CraftChest;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftCreature;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import org.bukkit.entity.Creature;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_14_R1.ChatMessageType;
import net.minecraft.server.v1_14_R1.EntityCreature;
import net.minecraft.server.v1_14_R1.EntityLiving;
import net.minecraft.server.v1_14_R1.IChatBaseComponent;
import net.minecraft.server.v1_14_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_14_R1.PacketPlayOutChat;

public class V1_14_R1 implements NMS {

	@Override
	public void setTarget(final Creature creature, final LivingEntity target) {
		final CraftCreature craftCreature = (CraftCreature) creature;
	    final EntityCreature nms = craftCreature.getHandle();
	    if (target == null) {
	        nms.setGoalTarget(null, null, false);
	    } else {
	        nms.setGoalTarget(((CraftLivingEntity)target).getHandle(), null, false);
	    }
	}

	@Override
	public void setTargetToClosestPlayer(final Creature creature, final Player player) {
		final CraftLivingEntity craftLiving = (CraftLivingEntity) player;
		final EntityLiving entityLiving = craftLiving.getHandle();
		((CraftCreature) creature).getHandle().setGoalTarget(entityLiving, TargetReason.CLOSEST_PLAYER, false);
	}

	@Override
	public void sendActionBarMessage(final Player player, final String message) {
		final IChatBaseComponent dummyComponent = ChatSerializer.a("{\"text\":\"herobrine1337\"}");
		final PacketPlayOutChat packet = new PacketPlayOutChat(dummyComponent, ChatMessageType.GAME_INFO);
		packet.components = new BaseComponent[] { new TextComponent(message) };
		final CraftPlayer craftPlayer = (CraftPlayer) player;
		craftPlayer.getHandle().playerConnection.sendPacket(packet);
	}

	@Override
	public void sendTitle(final Player player, final String title, final String subtitle) {
		((CraftPlayer) player).sendTitle(title, subtitle);
	}

	@Override
	public void setChestName(final Chest chest, final String name) {
		final CraftChest craftChest = (CraftChest) chest;
		try {
			final Field inventoryField = craftChest.getClass().getDeclaredField("chest");
			inventoryField.setAccessible(true);
			craftChest.setCustomName(name);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

}
