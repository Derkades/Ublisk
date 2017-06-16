package xyz.derkades.ublisk.iconmenus.help;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import xyz.derkades.ublisk.Message;
import xyz.derkades.ublisk.iconmenus.MainMenu;
import xyz.derkades.ublisk.utils.Menu;
import xyz.derkades.ublisk.utils.UPlayer;
import xyz.derkades.ublisk.utils.inventory.Item;

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
	}
	
	@Override
	public List<MenuItem> getMenuItems(Player player) {
		List<MenuItem> items = new ArrayList<>();
		
		int i = 0;
		for (Value value : Value.values()){
			ItemStack icon = new Item(Material.INK_SACK).setDamage(8).getItemStack();
			items.add(new MenuItem(i, icon, value.getName(), value.getDescription()));
			i++;
		}
		items.add(new MenuItem(17, new ItemStack(Material.BARRIER), "Back"));
		
		return items;
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
	
	private static enum Value {
		
		COMMANDS("Commands", "Help for commands");
		
		private String name;
		private String[] description;
		
		Value(String name, String... description){
			this.name = name;
			this.description = description;
		}
		
		private String getName(){ return name; }
		private String[] getDescription(){ return description; }
		
	}

}
