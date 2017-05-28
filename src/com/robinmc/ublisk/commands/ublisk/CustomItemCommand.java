package com.robinmc.ublisk.commands.ublisk;

import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.inventory.CustomItem;
import com.robinmc.ublisk.utils.inventory.UInventory;

public class CustomItemCommand extends UbliskCommand {

	@Override
	protected void onCommand(UPlayer player, String[] args) {
		UInventory inv = player.getInventory();
		inv.addItem(new CustomItem("test"));
		inv.addItem(new CustomItem("happy"));
		inv.addItem(new CustomItem("sad"));
	}

	@Override
	protected String getDescription() {
		return "Test custom item";
	}

	@Override
	protected String[] getAliases() {
		return new String[]{"item"};
	}
	
	

}
