package xyz.derkades.ublisk.money;

import net.md_5.bungee.api.ChatColor;
import xyz.derkades.derkutils.bukkit.ItemBuilder;
import xyz.derkades.derkutils.bukkit.menu.OptionClickEvent;
import xyz.derkades.ublisk.Message;
import xyz.derkades.ublisk.utils.Menu;
import xyz.derkades.ublisk.utils.UPlayer;
import xyz.derkades.ublisk.utils.inventory.Item;
import xyz.derkades.ublisk.utils.inventory.UInventory;

public class BankMenu extends Menu {

	public BankMenu(final UPlayer player) {
		super("Bank", 6*9, player);

		this.items.put(4, new ItemBuilder(new MoneyItem(MoneyItem.Type.BAR).getItemStack())
				.name(ChatColor.GOLD + "" + ChatColor.BOLD + "Bank")
				.lore(ChatColor.YELLOW + "Your balance: " + player.getMoney()).create());

		this.items.put(20, new ItemBuilder(new MoneyItem(MoneyItem.Type.NUGGET).getItemStack()).name("Deposit").lore("Gold nugget").create());
		this.items.put(22, new ItemBuilder(new MoneyItem(MoneyItem.Type.COIN).getItemStack()).name("Deposit").lore("Gold coin").create());
		this.items.put(24, new ItemBuilder(new MoneyItem(MoneyItem.Type.BAR).getItemStack()).name("Deposit").lore("Gold bar").create());

		this.items.put(29, new ItemBuilder(new MoneyItem(MoneyItem.Type.NUGGET).getItemStack()).name("Withdraw").lore("Gold nugget").create());
		this.items.put(31, new ItemBuilder(new MoneyItem(MoneyItem.Type.COIN).getItemStack()).name("Withdraw").lore("Gold coin").create());
		this.items.put(33, new ItemBuilder(new MoneyItem(MoneyItem.Type.BAR).getItemStack()).name("Withdraw").lore("Gold bar").create());
	}

	@Override
	public boolean onOptionClick(final OptionClickEvent event) {
		final UPlayer player = new UPlayer(event);
		final MoneyItem item = MoneyItem.fromItem(new Item(event.getItemStack()));

		if (item == null){
			player.sendMessage(Message.ERROR_MENU);
			return true;
		}

		final UInventory inv = player.getInventory();

		if (event.getName().contains("Deposit")){
			if (!inv.contains(item)){
				player.sendMessage(Message.INVENTORY_NOT_CONTAIN_MONEY_ITEM);
				return false;
			}

			player.setMoney(player.getMoney() + item.getValue());
			inv.remove(item);
		} else {
			if (player.getMoney() < item.getValue()){
				player.sendMessage(Message.NOT_ENOUGH_MONEY);
				return false;
			}

			player.setMoney(player.getMoney() - item.getValue());
			inv.addItem(item);
		}

		return false;
	}

}
