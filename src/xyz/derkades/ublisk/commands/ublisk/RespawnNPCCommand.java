package xyz.derkades.ublisk.commands.ublisk;

import xyz.derkades.ublisk.quest.NPC;
import xyz.derkades.ublisk.utils.UPlayer;

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
