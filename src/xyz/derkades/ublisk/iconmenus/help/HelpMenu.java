package xyz.derkades.ublisk.iconmenus.help;

import org.bukkit.Material;

import net.md_5.bungee.api.ChatColor;
import xyz.derkades.ublisk.Message;
import xyz.derkades.ublisk.iconmenus.MainMenu;
import xyz.derkades.ublisk.utils.ItemBuilder;
import xyz.derkades.ublisk.utils.Menu;
import xyz.derkades.ublisk.utils.UPlayer;

public class HelpMenu extends Menu {
	
	/*private static IconMenu menu = new IconMenu("Help", 2*9, new IconMenu.OptionClickEventHandler(){

		@Override
		public void onOptionClick(OptionClickEvent event) {
			String name = event.getName().toLowerCase();
			final UPlayer player = new UPlayer(event.getPlayer());
			event.setWillClose(false);
			if (name.contains("commands")){
				CommandsHelp.open(player);
			} else if (name.contains("faq")){
				// TODO Open faq menu
			} else if (name.equals("back")){
					new MainMenu(player).open();
			} else {
				player.sendMessage(Message.ERROR_MENU);
			}
		}
	});
	
	public static void open(UPlayer player){
		fillMenu();
		menu.open(player);
	}
	
	private static void fillMenu(){
		int i = 0;
		for (Value value : Value.values()){
			ItemStack icon = new Item(Material.INK_SACK).setDamage(8).getItemStack();
			menu.setOption(i, icon, value.getName(), value.getDescription());
			i++;
		}
		menu.setOption(17, new ItemStack(Material.BARRIER), "Back");
	}*/
	
	public HelpMenu(UPlayer player) {
		super("Help", 2*9, player);
		
		items.put(0, new ItemBuilder(Material.INK_SACK)
				.data(8)
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
