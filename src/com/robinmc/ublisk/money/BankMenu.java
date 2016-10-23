package com.robinmc.ublisk.money;

import org.bukkit.entity.Player;

import com.robinmc.ublisk.Message;
import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.inventory.BetterInventory;
import com.robinmc.ublisk.utils.logging.LogLevel;
import com.robinmc.ublisk.utils.logging.Logger;
import com.robinmc.ublisk.utils.third_party.IconMenu;
import com.robinmc.ublisk.utils.third_party.IconMenu.OptionClickEvent;

import net.md_5.bungee.api.ChatColor;

public class BankMenu {
	
	private static IconMenu menu = new IconMenu("Bank", 6*9, new IconMenu.OptionClickEventHandler(){

		@Override
		public void onOptionClick(OptionClickEvent event) {
			UPlayer player = UPlayer.get(event);
			MoneyItem item = MoneyItem.fromItemStack(event.getItem());
			
			if (item == null){
				player.sendMessage(Message.ERROR_MENU);
				return;
			}
			
			BetterInventory inv = player.getInventory();
			
			if (event.getName().contains("Deposit")){
				if (!inv.contains(item.getItem())){
					player.sendMessage(Message.INVENTORY_NOT_CONTAIN_MONEY_ITEM);
					event.setWillClose(false);
					return;
				}
				
				player.addMoney(item.getValue());
				inv.remove(item);
			} else {
				if (player.getMoney() < item.getValue()){
					player.sendMessage(Message.NOT_ENOUGH_MONEY);
					event.setWillClose(false);
					return;
				}
				
				player.removeMoney(item.getValue());
				inv.add(item);
			}
			
			event.setWillClose(false);
		}
	});
	
	public static void open(Player player){
		Logger.log(LogLevel.INFO, "Menu", "Bank menu has been opened for " + player.getName());
		fillMenu(UPlayer.get(player));
		menu.open(player);
	}
	
	private static void fillMenu(UPlayer player){
		menu.fillEdgesWithGlass();
		menu.setOption(4, MoneyItem.BAR.getItem(),
				ChatColor.GOLD + "" + ChatColor.BOLD + "Bank",
				ChatColor.YELLOW + "Your balance: " + player.getMoney());
		
		menu.setOption(20, MoneyItem.NUGGET.getItem(), "Deposit", "Gold nugget");
		menu.setOption(22, MoneyItem.COIN.getItem(), "Deposit", "Gold coin");
		menu.setOption(24, MoneyItem.BAR.getItem(), "Deposit", "Gold bar");
		
		menu.setOption(29, MoneyItem.NUGGET.getItem(), "Withdraw", "Gold nugget");
		menu.setOption(31, MoneyItem.COIN.getItem(), "Withdraw", "Gold coin");
		menu.setOption(33, MoneyItem.BAR.getItem(), "Withdraw", "Gold bar");
		
	}

}
