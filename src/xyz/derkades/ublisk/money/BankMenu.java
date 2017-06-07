package xyz.derkades.ublisk.money;

import net.md_5.bungee.api.ChatColor;
import xyz.derkades.ublisk.Message;
import xyz.derkades.ublisk.utils.IconMenu;
import xyz.derkades.ublisk.utils.Logger;
import xyz.derkades.ublisk.utils.UPlayer;
import xyz.derkades.ublisk.utils.IconMenu.OptionClickEvent;
import xyz.derkades.ublisk.utils.Logger.LogLevel;
import xyz.derkades.ublisk.utils.inventory.Item;
import xyz.derkades.ublisk.utils.inventory.UInventory;

public class BankMenu {
	
	private static IconMenu menu = new IconMenu("Bank", 6*9, new IconMenu.OptionClickEventHandler(){

		@Override
		public void onOptionClick(OptionClickEvent event) {
			UPlayer player = new UPlayer(event.getPlayer());
			MoneyItem item = MoneyItem.fromItem(new Item(event.getItem()));
			
			if (item == null){
				player.sendMessage(Message.ERROR_MENU);
				return;
			}
			
			UInventory inv = player.getInventory();
			
			if (event.getName().contains("Deposit")){
				if (!inv.contains(item.getItem())){
					player.sendMessage(Message.INVENTORY_NOT_CONTAIN_MONEY_ITEM);
					event.setWillClose(false);
					return;
				}
				
				player.setMoney(player.getMoney() + item.getValue());
				inv.remove(item.getItem());
			} else {
				if (player.getMoney() < item.getValue()){
					player.sendMessage(Message.NOT_ENOUGH_MONEY);
					event.setWillClose(false);
					return;
				}
				
				player.setMoney(player.getMoney() - item.getValue());
				inv.addItem(item.getItem());
			}
			
			event.setWillClose(false);
		}
	});
	
	public static void open(UPlayer player){
		Logger.log(LogLevel.INFO, "Menu", "Bank menu has been opened for " + player.getName());
		fillMenu(player);
		menu.open(player);
	}
	
	private static void fillMenu(UPlayer player){
		menu.fillEdgesWithGlass();
		menu.setOption(4, MoneyItem.BAR.getItem().getItemStack(),
				ChatColor.GOLD + "" + ChatColor.BOLD + "Bank",
				ChatColor.YELLOW + "Your balance: " + player.getMoney());
		
		menu.setOption(20, MoneyItem.NUGGET.getItem().getItemStack(), "Deposit", "Gold nugget");
		menu.setOption(22, MoneyItem.COIN.getItem().getItemStack(), "Deposit", "Gold coin");
		menu.setOption(24, MoneyItem.BAR.getItem().getItemStack(), "Deposit", "Gold bar");
		
		menu.setOption(29, MoneyItem.NUGGET.getItem().getItemStack(), "Withdraw", "Gold nugget");
		menu.setOption(31, MoneyItem.COIN.getItem().getItemStack(), "Withdraw", "Gold coin");
		menu.setOption(33, MoneyItem.BAR.getItem().getItemStack(), "Withdraw", "Gold bar");
		
	}

}
