package com.robinmc.ublisk.commands.ublisk;

import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.inventory.Item;
import com.robinmc.ublisk.utils.inventory.UInventory;
import com.robinmc.ublisk.weapons.Weapon;
import com.robinmc.ublisk.weapons.sword.Sword;

public class SwordCommand extends UbliskCommand {

	@Override
	protected void onCommand(UPlayer player, String[] args) {
		UInventory inv = player.getInventory();

		for (Weapon weapon : Weapon.WEAPONS) {
			if (weapon instanceof Sword) {
				inv.addItem(new Item(((Sword) weapon).getItemStack()));
			}
		}
	}

	@Override
	protected String getDescription() {
		return "Gives all swords in Weapon enum";
	}

	@Override
	protected String[] getAliases() {
		return new String[]{"sword"};
	} 

}
