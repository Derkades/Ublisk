package xyz.derkades.ublisk.iconmenus;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import xyz.derkades.ublisk.Message;
import xyz.derkades.ublisk.iconmenus.help.HelpMenu;
import xyz.derkades.ublisk.money.BankMenu;
import xyz.derkades.ublisk.money.MoneyItem;
import xyz.derkades.ublisk.utils.IconMenu;
import xyz.derkades.ublisk.utils.UPlayer;
import xyz.derkades.ublisk.utils.IconMenu.OptionClickEvent;
import xyz.derkades.ublisk.utils.inventory.Item;

public class MainMenu {
	
	private static IconMenu menu = new IconMenu("Main menu", 1*9, new IconMenu.OptionClickEventHandler(){

		@Override
		public void onOptionClick(OptionClickEvent event) {
			String name = event.getName().toLowerCase();
			final UPlayer player = new UPlayer(event.getPlayer());
			event.setWillClose(false);
			if (name.equals("settings")){
				SettingsMenu.open(player); 
			} else if (name.equals("voting")){
				VotingMenu.open(player);
			} else if (name.equals("friends")){
				FriendsMenu.open(player);
			} else if (name.equals("help")){
				HelpMenu.open(player);
			} else if (name.equals("bank")){
				BankMenu.open(player);
			} else {
				player.sendMessage(Message.ERROR_MENU);
			}
		}
	});
	
	public static void open(UPlayer player){
		fillMenu(player);
		menu.open(player);
	}
	
	private static void fillMenu(UPlayer player){
		menu.setOption(0, new ItemStack(Material.REDSTONE_COMPARATOR), "Settings", "Toggle various options on and off");
		menu.setOption(1, new ItemStack(Material.PAPER), "Voting");
		menu.setOption(2, new Item(player.getName()).getItemStack(), "Friends");
		menu.setOption(3, new Item("MHF_Question").getItemStack(), "Help", "Help for commands and more");
		menu.setOption(4, MoneyItem.BAR.getItem().getItemStack(), "Bank", "This will later be removed and", "replaced with a proper bank");
	}

}
