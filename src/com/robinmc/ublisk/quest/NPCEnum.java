package com.robinmc.ublisk.quest;

import com.robinmc.ublisk.quest.npc.Alvin;
import com.robinmc.ublisk.quest.npc.Arzhur;
import com.robinmc.ublisk.quest.npc.Asher;
import com.robinmc.ublisk.quest.npc.David;
import com.robinmc.ublisk.quest.npc.Dianh;
import com.robinmc.ublisk.quest.npc.Jerrijn;
import com.robinmc.ublisk.quest.npc.Merek;
import com.robinmc.ublisk.quest.npc.Rasmus;
import com.robinmc.ublisk.quest.npc.Roy;
import com.robinmc.ublisk.quest.npc.TestNPC;
import com.robinmc.ublisk.quest.npc.Ulric;
import com.robinmc.ublisk.quest.npc.Zoltar;

public enum NPCEnum {
	
	TEST_NPC(new TestNPC()),
	
	DAVID(new David()),
	MEREK(new Merek()),
	ULRIC(new Ulric()),
	ARZHUR(new Arzhur()),
	ASHER(new Asher()),
	RASMUS(new Rasmus()),
	DIANH(new Dianh()),
	ZOLTAR(new Zoltar()),
	ALVIN(new Alvin()),
	
	JERRIJN(new Jerrijn()),
	ROY(new Roy());
	
	private NPC npc;
	
	NPCEnum(NPC npc){
		this.npc = npc;
	}
	
	public NPC getNPC(){
		return npc;
	}

}
