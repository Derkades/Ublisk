package xyz.derkades.ublisk.iconmenus;

import org.bukkit.Material;

import xyz.derkades.ublisk.Message;
import xyz.derkades.ublisk.iconmenus.help.HelpMenu;
import xyz.derkades.ublisk.money.BankMenu;
import xyz.derkades.ublisk.money.MoneyItem;
import xyz.derkades.ublisk.utils.ItemBuilder;
import xyz.derkades.ublisk.utils.Menu;
import xyz.derkades.ublisk.utils.UPlayer;

public class MainMenu extends Menu {

	public MainMenu(UPlayer player) {
		super("Main menu", 9, player);
		
		items.put(0, new ItemBuilder(Material.REDSTONE_COMPARATOR).name("Settings").lore("Toggle various options on and off").create());
		items.put(1, new ItemBuilder(Material.PAPER).name("Voting").create());
		items.put(2, new ItemBuilder(player.getName()).name("Friends").create());
		items.put(3, new ItemBuilder("MHF_Question").name("Help").lore("Help for commands and more").create());
		items.put(4, new ItemBuilder(MoneyItem.BAR.getItem()).name("Bank").lore("This will later be removed and", "replaced with a proper bank").create());
	}

	@Override
	public boolean onOptionClick(OptionClickEvent event) {
		String name = event.getName().toLowerCase();
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
