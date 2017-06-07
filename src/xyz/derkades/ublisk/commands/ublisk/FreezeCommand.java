package xyz.derkades.ublisk.commands.ublisk;

import xyz.derkades.ublisk.modules.PlayerFreeze;
import xyz.derkades.ublisk.utils.UPlayer;

public class FreezeCommand extends UbliskCommand {

	@Override
	protected void onCommand(UPlayer player, String[] args) {
		if (PlayerFreeze.isFrozen(player.getPlayer())){
			PlayerFreeze.setFrozen(player.getPlayer(), false);
			player.sendMessage("unfrozen");
		} else {
			PlayerFreeze.setFrozen(player.getPlayer(), true);
			player.sendMessage("frozen");
		}
	}

	@Override
	protected String getDescription() {
		return "Freezes the player";
	}

	@Override
	protected String[] getAliases() {
		return new String[]{"freeze"};
	}

}
