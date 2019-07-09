package xyz.derkades.ublisk.commands.ublisk;

import xyz.derkades.ublisk.modules.Loot;
import xyz.derkades.ublisk.utils.UPlayer;

public class RemoveLootCommand extends UbliskCommand {

	@Override
	protected void onCommand(UPlayer player, String[] args) {
		Loot.removeLootChests();
		player.sendMessage("Removed loot.");
	}

	@Override
	protected String getDescription() {
		return "Removes all loot chests";
	}

	@Override
	protected String[] getAliases() {
		return new String[]{"removeloot"};
	}

}
