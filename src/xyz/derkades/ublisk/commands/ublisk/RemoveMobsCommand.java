package xyz.derkades.ublisk.commands.ublisk;

import xyz.derkades.ublisk.Message;
import xyz.derkades.ublisk.mob.Mobs;
import xyz.derkades.ublisk.utils.UPlayer;
import xyz.derkades.ublisk.utils.Ublisk;

public class RemoveMobsCommand extends UbliskCommand {

	@Override
	protected void onCommand(UPlayer player, String[] args) {
		Ublisk.broadcastMessage(Message.ENTITIES_REMOVED);
		Mobs.clearMobs();
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
