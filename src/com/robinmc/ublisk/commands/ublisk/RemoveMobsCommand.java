package com.robinmc.ublisk.commands.ublisk;

import com.robinmc.ublisk.Message;
import com.robinmc.ublisk.mob.Mob;
import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.Ublisk;

public class RemoveMobsCommand extends UbliskCommand {

	@Override
	protected void onCommand(UPlayer player, String[] args) {
		Ublisk.broadcastMessage(Message.ENTITIES_REMOVED);
		Mob.removeMobs();
	}

	@Override
	protected String getDescription() {
		return "Removes entities, potentially improving server performance.";
	}

	@Override
	protected String[] getAliases() {
		return new String[]{"kill", "removemobs", "clearlag"};
	}

}
