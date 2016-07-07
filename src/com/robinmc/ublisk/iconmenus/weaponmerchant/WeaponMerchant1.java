package com.robinmc.ublisk.iconmenus.weaponmerchant;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.Messages;
import com.robinmc.ublisk.utils.Console;
import com.robinmc.ublisk.utils.IconMenu;
import com.robinmc.ublisk.utils.IconMenu.OptionClickEvent;

public class WeaponMerchant1 {
	
	private static IconMenu menu = new IconMenu("Weapon Merchant - Level 1", 1*9, new IconMenu.OptionClickEventHandler(){

		@Override
		public void onOptionClick(OptionClickEvent event) {
			String name = event.getName().toLowerCase();
			final Player player = event.getPlayer();
			if (name.contains("wooden")){
				//Weapon.giveWeapon(player, Weapon.OLD_WOODEN_SWORD);
			} else {
				player.sendMessage(Messages.menuErrorWrongItem());
			}
		}
	}, Main.getInstance());
	
	public static void open(Player player){
		Console.sendMessage("[Menus] Weapon Merchant - Level 1 has been opened for " + player.getName());
		fillMenu();
		menu.open(player);
	}
	
	private static void fillMenu(){
		menu.setOption(4, new ItemStack(Material.WOOD_SWORD), "Old Wooden Sword");
	}

}
