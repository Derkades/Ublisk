package com.robinmc.ublisk;

import com.robinmc.ublisk.utils.NPCUtils;

import de.inventivegames.npc.NPC;
import de.inventivegames.npc.NPCLib;

public class NPCs {
	
	public static void spawnAll(){
		NPCUtils npc = new NPCUtils();
		npc.spawnNPC(33, 67, -38, -70, 0, "Merek", "Lunrpulse");
	}
	
	public static void despawnAll(){
		for (NPC npc: NPCLib.getNPCs(Var.world())){
			npc.despawn();
		}
	}
	
}