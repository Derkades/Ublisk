package xyz.derkades.ublisk.iconmenus;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import xyz.derkades.derkutils.bukkit.ItemBuilder;
import xyz.derkades.ublisk.Message;
import xyz.derkades.ublisk.iconmenus.help.HelpMenu;
import xyz.derkades.ublisk.money.BankMenu;
import xyz.derkades.ublisk.money.MoneyItem;
import xyz.derkades.ublisk.utils.Menu;
import xyz.derkades.ublisk.utils.UPlayer;

public class MainMenu extends Menu {

	public MainMenu(UPlayer player) {
		super("Main menu", 9, player);
	}

	@Override
	public List<MenuItem> getMenuItems(Player player) {
		return Arrays.asList(
				new MenuItem(0, Material.REDSTONE_COMPARATOR, "Settings", "Toggle various options on and off"),
				new MenuItem(1, Material.PAPER, "Voting"),
				new MenuItem(2, new ItemBuilder(player.getName()).create(), "Friends"),
				new MenuItem(3, new ItemBuilder("MHF_Question").create(), "Help", "Help for commands and more"),
				new MenuItem(4, MoneyItem.BAR.getItem().getItemStack(), "Bank", "This will later be removed and", "replaced with a proper bank")
			);
	}

	@Override
	public boolean onOptionClick(OptionClickEvent event) {
		String name = event.getName().toLowerCase();
		final UPlayer player = new UPlayer(event.getPlayer());

		if (name.equals("settings")){
			new SettingsMenu(player).open();
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
		
		return false;
	}

}
