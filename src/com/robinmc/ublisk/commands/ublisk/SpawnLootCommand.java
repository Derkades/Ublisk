package com.robinmc.ublisk.commands.ublisk;

import com.robinmc.ublisk.Loot;
import com.robinmc.ublisk.utils.UPlayer;

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
