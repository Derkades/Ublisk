package com.robinmc.ublisk.commands.ublisk;

import com.robinmc.ublisk.modules.TPS;
import com.robinmc.ublisk.utils.UPlayer;

public class TPSCommand extends UbliskCommand {

	@Override
	protected void onCommand(UPlayer player, String[] args) {
		player.sendMessage("TPS: " + TPS.getAverageTPS());
	}

	@Override
	protected String getDescription() {
		return "Get current average TPS";
	}

	@Override
	protected String[] getAliases() {
		return new String[]{"lag", "tps"};
	}

}
