package xyz.derkades.ublisk.iconmenus.help;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import xyz.derkades.ublisk.Message;
import xyz.derkades.ublisk.iconmenus.MainMenu;
import xyz.derkades.ublisk.utils.IconMenu;
import xyz.derkades.ublisk.utils.UPlayer;
import xyz.derkades.ublisk.utils.IconMenu.OptionClickEvent;
import xyz.derkades.ublisk.utils.inventory.Item;

public class HelpMenu {
	
	private static IconMenu menu = new IconMenu("Help", 2*9, new IconMenu.OptionClickEventHandler(){

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
