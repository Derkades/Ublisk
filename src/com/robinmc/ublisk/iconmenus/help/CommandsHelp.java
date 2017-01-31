package com.robinmc.ublisk.iconmenus.help;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.utils.IconMenu;
import com.robinmc.ublisk.utils.IconMenu.OptionClickEvent;
import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.inventory.ItemBuilder;

import net.md_5.bungee.api.ChatColor;

public class CommandsHelp {
	
	private static IconMenu menu = new IconMenu("Commands", 2*9, new IconMenu.OptionClickEventHandler(){

		@Override
		public void onOptionClick(final OptionClickEvent event) {
			event.setWillClose(false);
			if (event.getName().equals("Back")){
				HelpMenu.open(new UPlayer(event.getPlayer()));
			}
		}
	});
	
	public static void open(UPlayer player){
		fillMenu();
		menu.open(player);
	}
	
	private static void fillMenu(){
		int i = 0;
		
		Map<String, String> commands = new HashMap<>();
		
		//Put these commands here in reverse order!!
		commands.put("AFK", "afk");
		commands.put("Private Messaging", "pm");
		commands.put("Guilds", "guild");
		
		for (Entry<String, String> entry : commands.entrySet()){
			ItemStack icon = new ItemBuilder(Material.INK_SACK).setDamage(8).getItemStack();
			List<String> loreLines = new ArrayList<String>();
			loreLines.add(getDescription(entry.getValue()));
			
			loreLines.add("");
			
			String[] usageLines = getUsage(entry.getValue()).split("\n");
			for (String usageLine : usageLines) loreLines.add(ChatColor.RED + usageLine);
			
			menu.setOption(i, icon, entry.getKey() + " - /" + entry.getValue(), loreLines.toArray(new String[]{}));
			
			i++;
		}
		
		/*
		for (Value value : Value.values()){
			ItemStack icon = new ItemBuilder(Material.INK_SACK).setDamage(8).getItemStack();
			menu.setOption(i, icon, value.getName(), value.getDescription());
			i++;
		}*/
		menu.setOption(17, new ItemStack(Material.BARRIER), "Back");
	}
	
	private static String getUsage(String command){
		return Main.getInstance().getCommand(command).getUsage();
	}
	
	private static String getDescription(String command){
		return Main.getInstance().getCommand(command).getDescription();
	}
	
	/*
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
		
	}*/

}
