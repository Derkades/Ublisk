package com.robinmc.ublisk.commands.ublisk;

import org.bukkit.inventory.PlayerInventory;

import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.weapons.Weapon;
import com.robinmc.ublisk.weapons.sword.Sword;

public class SwordCommand extends UbliskCommand {

	@Override
	protected void onCommand(UPlayer player, String[] args) {
		PlayerInventory inv = player.getInventory();

		for (Weapon weapon : Weapon.getWeapons()) {
			if (weapon instanceof Sword) {
				inv.addItem(((Sword) weapon).getItemStack());
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
