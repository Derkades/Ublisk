package com.robinmc.ublisk.commands.ublisk;

import com.robinmc.ublisk.Loot;
import com.robinmc.ublisk.Loot.LootChest;
import com.robinmc.ublisk.utils.UPlayer;

public class RemoveLootCommand extends UbliskCommand {

	@Override
	protected void onCommand(UPlayer player, String[] args) {
		for (LootChest loot : Loot.getLootChests()){
			loot.remove();
		}
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
