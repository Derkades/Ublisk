package com.robinmc.ublisk.utils.quest;

import com.robinmc.ublisk.utils.variable.Var;

import de.inventivegames.npc.NPC;
import de.inventivegames.npc.NPCLib;

public enum QuestCharacter {
	/*
	npc.spawnNPC(33, 67, -38, -70, 0, "Merek", "MrCaMeRoOn_01");
	npc.spawnNPC(38.5, 67, -26.5, -145, 0, "Ulric", "Chaspyr");
	npc.spawnNPC(111.5, 68, -103.5, -20, 0, "Arzhur", "Notch"); //FIXME: Arzhur skin
	npc.spawnNPC(449.3, 70, -10.5, 75, 5, "Asher", "MrParkerl11"); //FIXME: Asher skin
	*/
	MEREK("Merek", "MrCaMeRoOn_01", new NPCLocation(1, 1, 1, 1, 1)),
	ULRIC("Ulric", "Chaspyr", new NPCLocation(1, 1, 1, 1, 1)),
	ARZHUR("Arzhur", "Notch", new NPCLocation(1, 1, 1, 1, 1)),
	ASHER("Asher", "MrParkerl11", new NPCLocation(1, 1, 1, 1, 1));
	
	private String name;
	private String skin;
	private NPCLocation loc;
	
	QuestCharacter(String name, String skin, NPCLocation loc){
		this.name = name;
		this.loc = loc;
		this.skin = skin;
	}
	
	public String getName(){
		return name;
	}
	
	public String getSkin(){
		return skin;
	}
	
	public NPCLocation getLocation(){
		return loc;
	}
	
	public static void spawnAll(){
		NPCUtils api = new NPCUtils();
		for (QuestCharacter npc : QuestCharacter.getAll()){
			api.spawnNPC(npc);
		}
	}
	
	public static void despawnAll(){
		for (NPC npc: NPCLib.getNPCs(Var.world())){
			npc.despawn();
		}
	}
	
	public static QuestCharacter[] getAll(){
		return QuestCharacter.values();
	}
	
}
