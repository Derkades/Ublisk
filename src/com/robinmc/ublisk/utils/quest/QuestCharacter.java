package com.robinmc.ublisk.utils.quest;

import java.util.ArrayList;
import java.util.List;

import com.robinmc.ublisk.npc.Alvin;
import com.robinmc.ublisk.npc.Arzhur;
import com.robinmc.ublisk.npc.Dianh;
import com.robinmc.ublisk.npc.Merek;
import com.robinmc.ublisk.npc.Rasmus;
import com.robinmc.ublisk.npc.Ulric;
import com.robinmc.ublisk.npc.Zoltar;

public enum QuestCharacter {
	
	MEREK(new Merek(), "Merek", "MrCaMeRoOn_01", new NPCLocation(33, 67, -38, -70, 0)),
	ULRIC(new Ulric(), "Ulric", "Chaspyr", new NPCLocation(38.5, 67, -26.5, -145, 0)),
	ARZHUR(new Arzhur(), "Arzhur", "Notch", new NPCLocation(111.5, 68, -103.5, -20, 0)), // TODO Arzhur skin
	ASHER(null, "Asher", "MrParkerl11", new NPCLocation(449.3, 70, -10.5, 75, 5)), // TODO Asher skin
	RASMUS(new Rasmus(), "Rasmus", "RobinMC", new NPCLocation(1, 1, 1, 1, 1)), // TODO Rasmus skin TODO Rasmus coordinates
	DIANH(new Dianh(), "Dianh", ":D", new NPCLocation(1, 1, 1, 1, 1)),// TODO coordinaten en skin van Dianh
	ZOLTAR(new Zoltar(), "Zoltar", ":D", new NPCLocation(1, 1, 1, 1, 1)),// TODO coordinaten en skin van Zoltar
	ALVIN(new Alvin(), "Alvin", ":D", new NPCLocation(1, 1, 1, 1, 1));
	
	private QuestCharacterClass qcc;
	private String name;
	private String skin;
	private NPCLocation loc;
	
	QuestCharacter(QuestCharacterClass qcc, String name, String skin, NPCLocation loc){
		this.name = name;
		this.loc = loc;
		this.skin = skin;
	}
	
	public QuestCharacterClass getQuestCharacterClass(){
		return qcc;
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
	
	public static QuestCharacter fromString(String text) throws IllegalArgumentException {
		if (text == null) {
			throw new IllegalArgumentException();
		}
		
		for (QuestCharacter npc : QuestCharacter.values()) {
			if (text.equalsIgnoreCase(npc.name)) {
				return npc;
			}
		}
		
		throw new IllegalArgumentException();
		
	}
	
	public static List<String> getAllNames(){
		List<String> list = new ArrayList<String>();
		for (QuestCharacter npc : QuestCharacter.values()){
			list.add(npc.getName());
		}
		return list;
	}
	
}
