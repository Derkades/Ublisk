package xyz.derkades.ublisk.iconmenus;

import org.bukkit.Bukkit;
import org.bukkit.Material;

import xyz.derkades.derkutils.bukkit.menu.OptionClickEvent;
import xyz.derkades.ublisk.Message;
import xyz.derkades.ublisk.iconmenus.help.HelpMenu;
import xyz.derkades.ublisk.money.BankMenu;
import xyz.derkades.ublisk.money.MoneyItem;
import xyz.derkades.ublisk.utils.ItemBuilder;
import xyz.derkades.ublisk.utils.Menu;
import xyz.derkades.ublisk.utils.UPlayer;

public class MainMenu extends Menu {

	public MainMenu(final UPlayer player) {
		super("Main menu", 9, player);

		this.items.put(0, new ItemBuilder(Material.COMPARATOR).name("Settings").lore("Toggle various options on and off").create());
		this.items.put(1, new ItemBuilder(Material.PAPER).name("Voting").create());
		this.items.put(2, new ItemBuilder(player).name("Friends").create());
		// TODO Use UUID
		this.items.put(3, new ItemBuilder(Bukkit.getOfflinePlayer("MHF_Question")).name("Help").lore("Help for commands and more").create());
		this.items.put(4, new ItemBuilder(new MoneyItem(MoneyItem.Type.BAR).getItemStack()).name("Bank").lore("This will later be removed and", "replaced with a proper bank").create());
	}

	@Override
	public boolean onOptionClick(final OptionClickEvent event) {
		final String name = event.getName().toLowerCase();
		final UPlayer player = new UPlayer(event.getPlayer());

		if (name.equals("settings")){
			new SettingsMenu(player).open();
		} else if (name.equals("voting")){
			new VotingMenu(player).open();
		} else if (name.equals("friends")){
			new FriendsMenu(player).open();
		} else if (name.equals("help")){
			new HelpMenu(player).open();
		} else if (name.equals("bank")){
			new BankMenu(player).open();
		} else {
			player.sendMessage(Message.ERROR_MENU);
		}

		return false;
	}

}
