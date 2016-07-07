package com.robinmc.ublisk;

import com.robinmc.ublisk.utils.NPCUtils;

import de.inventivegames.npc.NPC;
import de.inventivegames.npc.NPCLib;

public class NPCs {
	
	public static void spawnAll(){
		NPCUtils npc = new NPCUtils();
		
		npc.spawnNPC(33, 67, -38, -70, 0, "Merek", "MrCaMeRoOn_01");
		npc.spawnNPC(38.5, 67, -26.5, -145, 0, "Ulric", "Chaspyr");
		npc.spawnNPC(111.5, 68, -103.5, -20, 0, "Arzhur", "Notch"); //FIXME: Arzhur skin
		
		
		/*
		npc.spawnNPC(33, 67, -38, -70, 0, "Merek");
		npc.spawnNPC(38.5, 67, -26.5, -145, 0, "Ulric");
		npc.spawnNPC(111.5, 68, -103.5, -20, 0, "Arzhur");
		*/
	}
	
	public static void despawnAll(){
		for (NPC npc: NPCLib.getNPCs(Var.world())){
			npc.despawn();
		}
	}
	
}
