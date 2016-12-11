package com.robinmc.ublisk.iconmenus;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.robinmc.ublisk.Message;
import com.robinmc.ublisk.iconmenus.help.HelpMenu;
import com.robinmc.ublisk.money.BankMenu;
import com.robinmc.ublisk.money.MoneyItem;
import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.inventory.item.ItemBuilder;
import com.robinmc.ublisk.utils.logging.LogLevel;
import com.robinmc.ublisk.utils.logging.Logger;
import com.robinmc.ublisk.utils.perm.PermissionGroup;
import com.robinmc.ublisk.utils.third_party.IconMenu;
import com.robinmc.ublisk.utils.third_party.IconMenu.OptionClickEvent;

public class MainMenu {
	
	private static IconMenu menu = new IconMenu("Main menu", 1*9, new IconMenu.OptionClickEventHandler(){

		@Override
		public void onOptionClick(OptionClickEvent event) {
			String name = event.getName().toLowerCase();
			final Player player = event.getPlayer();
			event.setWillClose(false);
			if (name.equals("settings")){
				SettingsMenu.open(player); 
			} else if (name.equals("voting")){
				VotingMenu.open(player);
			} else if (name.equals("friends")){
				FriendsMenu.open(player);
			} else if (name.equals("staff settings")){
				StaffSettingsMenu.open(player);
			} else if (name.equals("help")){
				HelpMenu.open(player);
			} else if (name.equals("bank")){
				BankMenu.open(player);
			} else {
				player.sendMessage(Message.ERROR_MENU.get());
			}
		}
	});
	
	public static void open(Player player){
		Logger.log(LogLevel.INFO, "Menu", "MainMenu has been opened for " + player.getName());
		fillMenu(player);
		menu.open(player);
	}
	
	private static void fillMenu(Player player){
		menu.setOption(0, new ItemStack(Material.REDSTONE_COMPARATOR), "Settings", "Toggle various options on and off");
		menu.setOption(1, new ItemStack(Material.PAPER), "Voting");
		menu.setOption(2, new ItemBuilder(player.getName()).getItemStack(), "Friends");
		menu.setOption(3, new ItemBuilder("MHF_Question").getItemStack(), "Help", "Help for commands and more");
		menu.setOption(4, MoneyItem.BAR.getItem(), "Bank", "This will later be removed and", "replaced with a proper bank");
		
		if (UPlayer.get(player).getGroup() != PermissionGroup.DEFAULT){
			menu.setOption(8, new ItemStack(Material.DIAMOND), "Staff settings");
		}
	}

}
