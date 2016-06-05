package com.robinmc.ublisk.utils;

import org.bukkit.Location;

import com.robinmc.ublisk.Var;

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
	public static void spawnNPC(int x, int y, int z, String name, String skin){
		Location loc = new Location(Var.world(), x, y, z);
		NPCLib.spawnPlayerNPC(loc, name, skin);
	}

}
