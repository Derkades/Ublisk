package xyz.derkades.ublisk.money;

import java.util.Arrays;
import java.util.List;

import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import xyz.derkades.ublisk.Message;
import xyz.derkades.ublisk.utils.Menu;
import xyz.derkades.ublisk.utils.UPlayer;
import xyz.derkades.ublisk.utils.inventory.Item;
import xyz.derkades.ublisk.utils.inventory.UInventory;

public class BankMenu extends Menu {

	public BankMenu(UPlayer player) {
		super("Bank", 6*9, player);
	}

	@Override
	public List<MenuItem> getMenuItems(Player player) {
		return Arrays.asList(
				new MenuItem(4, MoneyItem.BAR.getItem().getItemStack(),
						ChatColor.GOLD + "" + ChatColor.BOLD + "Bank",
						ChatColor.YELLOW + "Your balance: " + new UPlayer(player).getMoney()),
				
				new MenuItem(20, MoneyItem.NUGGET.getItem().getItemStack(), "Deposit", "Gold nugget"),
				new MenuItem(22, MoneyItem.COIN.getItem().getItemStack(), "Deposit", "Gold coin"),
				new MenuItem(24, MoneyItem.BAR.getItem().getItemStack(), "Deposit", "Gold bar"),
				
				new MenuItem(29, MoneyItem.NUGGET.getItem().getItemStack(), "Withdraw", "Gold nugget"),
				new MenuItem(31, MoneyItem.COIN.getItem().getItemStack(), "Withdraw", "Gold coin"),
				new MenuItem(33, MoneyItem.BAR.getItem().getItemStack(), "Withdraw", "Gold bar")
				);
	}

	@Override
	public boolean onOptionClick(OptionClickEvent event) {
		UPlayer player = new UPlayer(event);
		MoneyItem item = MoneyItem.fromItem(new Item(event.getItemStack()));
		
		if (item == null){
			player.sendMessage(Message.ERROR_MENU);
			return true;
		}
		
		UInventory inv = player.getInventory();
		
		if (event.getName().contains("Deposit")){
			if (!inv.contains(item.getItem())){
				player.sendMessage(Message.INVENTORY_NOT_CONTAIN_MONEY_ITEM);
				return false;
			}
			
			player.setMoney(player.getMoney() + item.getValue());
			inv.remove(item.getItem());
		} else {
			if (player.getMoney() < item.getValue()){
				player.sendMessage(Message.NOT_ENOUGH_MONEY);
				return false;
			}
			
			player.setMoney(player.getMoney() - item.getValue());
			inv.addItem(item.getItem());
		}
		
		return false;
	}
	
	/*private static IconMenu menu = new IconMenu("Bank", 6*9, new IconMenu.OptionClickEventHandler(){

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
		
	}*/

}
