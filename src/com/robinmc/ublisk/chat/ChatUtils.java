package com.robinmc.ublisk.chat;

import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_10_R1.IChatBaseComponent;
import net.minecraft.server.v1_10_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_10_R1.PacketPlayOutChat;

@Deprecated
public class ChatUtils {
	
	public static void sendJsonMessage(Player player, JSON json){
		IChatBaseComponent comp = ChatSerializer.a(json.getString());
        PacketPlayOutChat packet = new PacketPlayOutChat(comp);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
	}

}
