package com.robinmc.ublisk.commands.ublisk;

import com.robinmc.ublisk.quest.NPC;
import com.robinmc.ublisk.utils.UPlayer;

public class RespawnNPCCommand extends UbliskCommand {

	@Override
	protected void onCommand(UPlayer player, String[] args) {
		NPC.respawnAll();
	}

	@Override
	protected String getDescription() {
		return "Respawns all NPCs";
	}

	@Override
	protected String[] getAliases() {
		return new String[]{"npc", "respawnnpc", "respawnnpcs", "npcrespawn"};
	}

}
