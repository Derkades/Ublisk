package com.robinmc.ublisk.commands.ublisk;

import com.robinmc.ublisk.utils.Lag;
import com.robinmc.ublisk.utils.UPlayer;

public class TPSCommand extends UbliskCommand {

	@Override
	protected void onCommand(UPlayer player, String[] args) {
		player.sendMessage("TPS: " + Lag.getTPS());
	}

	@Override
	protected String getDescription() {
		return "Get current TPS (may not be very accurate)";
	}

	@Override
	protected String[] getAliases() {
		return new String[]{"lag", "tps"};
	}

}
