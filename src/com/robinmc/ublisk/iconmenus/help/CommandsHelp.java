package com.robinmc.ublisk.iconmenus.help;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.robinmc.ublisk.utils.IconMenu;
import com.robinmc.ublisk.utils.IconMenu.OptionClickEvent;
import com.robinmc.ublisk.utils.inventory.item.ItemBuilder;

public class CommandsHelp {
	
	private static IconMenu menu = new IconMenu("Commands", 2*9, new IconMenu.OptionClickEventHandler(){

		@Override
		public void onOptionClick(final OptionClickEvent event) {
			event.setWillClose(false);
			if (event.getName().equals("Back")){
				HelpMenu.open(event.getPlayer());
			}
		}
	});
	
	public static void open(Player player){
		fillMenu();
		menu.open(player);
	}
	
	private static void fillMenu(){
		int i = 0;
		for (Value value : Value.values()){
			ItemStack icon = new ItemBuilder(Material.INK_SACK).setDamage(8).getItemStack();
			menu.setOption(i, icon, value.getName(), value.getDescription());
			i++;
		}
		menu.setOption(17, new ItemStack(Material.BARRIER), "Back");
	}
	
	private static enum Value {
		
		AFK("Afk", 
				"/afk - Toggles AFK mode"),
		PRIVATE_MESSAGE("Private message",
				"/tell [player] [message] - Send a player a private message",
				"/r [message] - Quickly reply to the last person who sent you a message"),
		GUILDS("Guilds",
				"/guild create [name] - Create a guild",
				"/guild invite [player] - Invite someone to your guild",
				"/guild accept - Accept a pending guild request",
				"/guild leave - Leave your guild");
		
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
