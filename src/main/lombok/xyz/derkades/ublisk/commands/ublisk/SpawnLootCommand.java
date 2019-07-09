package xyz.derkades.ublisk.commands.ublisk;

import xyz.derkades.ublisk.modules.Loot;
import xyz.derkades.ublisk.utils.UPlayer;

public class SpawnLootCommand extends UbliskCommand {

	@Override
	protected void onCommand(UPlayer player, String[] args) {
		Loot.getRandomLoot().spawn();
	}

	@Override
	protected String getDescription() {
		return "Spawns a random loot chest";
	}

	@Override
	protected String[] getAliases() {
		return new String[]{"loot", "randomloot", "spawnloot"};
	}

}
