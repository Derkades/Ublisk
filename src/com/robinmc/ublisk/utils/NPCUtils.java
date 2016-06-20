package com.robinmc.ublisk.utils;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.robinmc.ublisk.Messages;
import com.robinmc.ublisk.Var;
import com.robinmc.ublisk.npc.Merek;

import de.inventivegames.npc.NPCLib;

public class NPCUtils {
	
	/**
	 * Spawns an NPC at the given location
	 * @param x
	 * @param y
	 * @param z
	 * @param name Name of NPC as will appear floating above it
	 * @param skin In-game name of the skin this NPC will have
	 */
	public void spawnNPC(int x, int y, int z, String name, String skin){
		Location loc = new Location(Var.world(), x, y, z);
		NPCLib.spawnPlayerNPC(loc, name, skin);
	}
	
	/**
	 * Spawns an NPC at the given location
	 * @param x
	 * @param y
	 * @param z
	 * @param pitch
	 * @param yaw
	 * @param name Name of NPC as will appear floating above it
	 * @param skin In-game name of the skin this NPC will have
	 */
	public void spawnNPC(double x, int y, double z, int pitch, int yaw, String name, String skin){
		Location loc = new Location(Var.world(), x, y, z, pitch, yaw);
		NPCLib.spawnPlayerNPC(loc, name, skin);
	}
	
	public void msg(Player player, String npc, String msg){
		player.sendMessage(Messages.npcMsg(npc, msg));
	}
	
	public void open(Player player, String name){
		if (name == "Merek"){
			Merek.merek(player);
		} else {
			player.sendMessage(Messages.npcNotFound(name));
		}
	}

}
