package xyz.derkades.ublisk.commands.ublisk;

import xyz.derkades.ublisk.modules.TPS;
import xyz.derkades.ublisk.utils.UPlayer;

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
