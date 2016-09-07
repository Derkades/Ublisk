package com.robinmc.ublisk.iconmenus;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.utils.logging.LogLevel;
import com.robinmc.ublisk.utils.logging.Logger;
import com.robinmc.ublisk.utils.scheduler.Scheduler;
import com.robinmc.ublisk.utils.third_party.IconMenu;
import com.robinmc.ublisk.utils.third_party.IconMenu.OptionClickEvent;
import com.robinmc.ublisk.utils.variable.Message;

public class MainMenu {
	
	private static IconMenu menu = new IconMenu("Main menu", 1*9, new IconMenu.OptionClickEventHandler(){

		@Override
		public void onOptionClick(OptionClickEvent event) {
			String name = event.getName().toLowerCase();
			final Player player = event.getPlayer();
			if (name.equals("settings")){
				Scheduler.runTaskLater(5, new Runnable(){ 
					public void run(){ 
						SettingsMenu.open(player); 
					}
				});
			} else if (name.equals("voting")){
				Scheduler.runTaskLater(5, new Runnable(){ 
					public void run(){ 
						VotingMenu.open(player);
					}
				});
			} else if (name.equals("friends")){
				Scheduler.runTaskLater(5, new Runnable(){ 
					public void run(){ 
						FriendsMenu.open(player);
					}
				});
			} else {
				player.sendMessage(Message.ERROR_MENU.get());
			}
		}
	}, Main.getInstance());
	
	public static void open(Player player){
		Logger.log(LogLevel.INFO, "Menu", "MainMenu has been opened for " + player.getName());
		fillMenu(player);
		menu.open(player);
	}
	
	private static void fillMenu(Player player){
		menu.setOption(0, new ItemStack(Material.REDSTONE_COMPARATOR), "Settings", "Toggle various options on and off");
		menu.setOption(1, new ItemStack(Material.PAPER), "Voting", "blah blah blah"); // TODO Lore
		ItemStack skull = new ItemStack(Material.SKULL_ITEM);
		skull.setDurability((short) 3);
		SkullMeta meta = (SkullMeta) skull.getItemMeta();
		meta.setOwner(player.getName());
		skull.setItemMeta(meta);
		menu.setOption(2, skull, "Friends", "blah blah blah"); // TODO Lore
	}

}
