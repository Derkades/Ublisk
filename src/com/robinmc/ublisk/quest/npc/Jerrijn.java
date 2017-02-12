package com.robinmc.ublisk.quest.npc;

import org.bukkit.entity.Villager.Profession;

import com.robinmc.ublisk.quest.NPC;
import com.robinmc.ublisk.quest.NPCInfo;
import com.robinmc.ublisk.quest.NPCInfo.NPCLocation;
import com.robinmc.ublisk.utils.UPlayer;

public class Jerrijn extends NPC {

	@Override
	public NPCInfo getNPCInfo() {
		return new NPCInfo("Jerrijn", Profession.NITWIT, false, new NPCLocation(678, 74, 387));
	}

	@Override
	public void talk(UPlayer player) {
		player.sendMessage("Town: "+ player.getTown().getName());
	}

}
