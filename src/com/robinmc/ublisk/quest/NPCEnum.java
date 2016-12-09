package com.robinmc.ublisk.quest;

import com.robinmc.ublisk.quest.npc.Alvin;
import com.robinmc.ublisk.quest.npc.Arzhur;
import com.robinmc.ublisk.quest.npc.Asher;
import com.robinmc.ublisk.quest.npc.David;
import com.robinmc.ublisk.quest.npc.Dianh;
import com.robinmc.ublisk.quest.npc.Merek;
import com.robinmc.ublisk.quest.npc.Rasmus;
import com.robinmc.ublisk.quest.npc.TestNPC;
import com.robinmc.ublisk.quest.npc.Ulric;
import com.robinmc.ublisk.quest.npc.Zoltar;

public enum NPCEnum {
	
	//FIXME Move TODOs
	
	TEST_NPC(new TestNPC()),
	
	DAVID(new David()),
	MEREK(new Merek()),
	ULRIC(new Ulric()),
	ARZHUR(new Arzhur()), // TODO Profession
	ASHER(new Asher()), // TODO Profession
	RASMUS(new Rasmus()), // TODO Rasmus coordinates XXX Profession
	DIANH(new Dianh()),// TODO Dianh coordinates XXX Profession
	ZOLTAR(new Zoltar()),// TODO Zoltar coordinates XXX Profession
	ALVIN(new Alvin()); // XXX Profession
	
	private NPC npc;
	
	NPCEnum(NPC npc){
		this.npc = npc;
	}
	
	public NPC getNPC(){
		return npc;
	}

}
