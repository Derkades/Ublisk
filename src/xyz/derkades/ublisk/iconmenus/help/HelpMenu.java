package xyz.derkades.ublisk.iconmenus.help;

import org.bukkit.Material;

import net.md_5.bungee.api.ChatColor;
import xyz.derkades.derkutils.bukkit.menu.OptionClickEvent;
import xyz.derkades.ublisk.Message;
import xyz.derkades.ublisk.iconmenus.MainMenu;
import xyz.derkades.ublisk.utils.ItemBuilder;
import xyz.derkades.ublisk.utils.Menu;
import xyz.derkades.ublisk.utils.UPlayer;

public class HelpMenu extends Menu {
	
	public HelpMenu(UPlayer player) {
		super("Help", 2*9, player);
		
		items.put(0, new ItemBuilder(Material.LIGHT_GRAY_DYE)
				.name(ChatColor.DARK_AQUA + "Commands")
				.lore(ChatColor.GRAY + "Help for commands")
				.create());
		
		items.put(17, new ItemBuilder(Material.BARRIER).coloredName("&cBack").create());
	}

	@Override
	public boolean onOptionClick(OptionClickEvent event) {
		String name = event.getName().toLowerCase();
		final UPlayer player = new UPlayer(event);
		if (name.contains("commands")){
			new CommandsHelp(player).open();
		} else if (name.contains("faq")){
			// TODO Open faq menu
		} else if (name.equals("back")){
				new MainMenu(player).open();
		} else {
			player.sendMessage(Message.ERROR_MENU);
		}
		return false;
	}

}
