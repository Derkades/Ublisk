package com.robinmc.ublisk.commands.ublisk;

import com.robinmc.ublisk.modules.Advancements;
import com.robinmc.ublisk.modules.Advancements.Advancement;
import com.robinmc.ublisk.utils.UPlayer;

public class DebugCommand extends UbliskCommand {

	@Override
	protected void onCommand(UPlayer player, String[] args) {
		//Feel free to delete everything below this to test your own stuff
		Advancements.grantAdvancement(player.getPlayer(), Advancement.QUESTS_ROOT);
	}

	@Override
	protected String getDescription() {
		return "Command for debugging purposes";
	}

	@Override
	protected String[] getAliases() {
		return new String[]{"debug"};
	}

}
