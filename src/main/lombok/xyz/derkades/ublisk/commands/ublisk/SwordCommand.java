package xyz.derkades.ublisk.commands.ublisk;

import xyz.derkades.ublisk.utils.UPlayer;
import xyz.derkades.ublisk.utils.inventory.Item;
import xyz.derkades.ublisk.utils.inventory.UInventory;
import xyz.derkades.ublisk.weapons.Weapon;
import xyz.derkades.ublisk.weapons.sword.Sword;

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
