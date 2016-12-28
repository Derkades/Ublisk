package com.robinmc.ublisk.quest.npc;

import com.robinmc.ublisk.quest.NPC;
import com.robinmc.ublisk.quest.NPCInfo;
import com.robinmc.ublisk.quest.NPCInfo.NPCLocation;
import com.robinmc.ublisk.utils.UPlayer;

public class Asher extends NPC {

	@Override
	public NPCInfo getNPCInfo() {
		return new NPCInfo("Asher", null, false, new NPCLocation(449.3, 70, -10.5));  // XXX Profession
	}
	
	@Override
	public void talk(UPlayer player) {
		
	}

}
