package com.robinmc.ublisk.quest.npc;

import org.bukkit.entity.Villager.Profession;

import com.robinmc.ublisk.quest.NPC;
import com.robinmc.ublisk.quest.NPCInfo;
import com.robinmc.ublisk.quest.NPCInfo.NPCLocation;
import com.robinmc.ublisk.quest.QuestParticipant;
import com.robinmc.ublisk.utils.UPlayer;

public class David extends NPC {

	@Override
	public NPCInfo getNPCInfo() {
		return new NPCInfo("David", Profession.FARMER, false, new NPCLocation(72.5, 67, -2.5));
	}
	
	@Override
	public void talk(UPlayer player) {
		final QuestParticipant qp = player.getQuestParticipant(null, this);
		qp.sendMessage("Hi!");
	}

}
