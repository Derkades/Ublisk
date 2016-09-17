package com.robinmc.ublisk.iconmenus;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.iconmenus.help.HelpMenu;
import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.inventory.item.ItemBuilder;
import com.robinmc.ublisk.utils.logging.LogLevel;
import com.robinmc.ublisk.utils.logging.Logger;
import com.robinmc.ublisk.utils.perm.PermissionGroup;
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
			// XXX Update to new one tick delay method
			if (name.equals("settings")){
				Scheduler.runTaskLater(1, new Runnable(){ 
					public void run(){ 
						SettingsMenu.open(player); 
					}
				});
			} else if (name.equals("voting")){
				Scheduler.runTaskLater(1, new Runnable(){ 
					public void run(){ 
						VotingMenu.open(player);
					}
				});
			} else if (name.equals("friends")){
				Scheduler.runTaskLater(1, new Runnable(){ 
					public void run(){ 
						FriendsMenu.open(player);
					}
				});
			} else if (name.equals("staff settings")){
				Scheduler.runTaskLater(1, new Runnable(){
					public void run(){
						StaffSettingsMenu.open(player);
					}
				});
			} else if (name.equals("help")){
				Scheduler.oneTickDelay(new Runnable(){
					public void run(){
						HelpMenu.open(player);
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
		menu.setOption(1, new ItemStack(Material.PAPER), "Voting");
		ItemStack skull = new ItemStack(Material.SKULL_ITEM);
		skull.setDurability((short) 3); // TODO Cleanup code with new ItemBuilder
		SkullMeta meta = (SkullMeta) skull.getItemMeta();
		meta.setOwner(player.getName());
		skull.setItemMeta(meta);
		menu.setOption(2, skull, "Friends");
		menu.setOption(3, new ItemBuilder(Material.SKULL_ITEM).setDamage(3).setSkullOwner("MHF_Question").getItemStack(), "Help", "Help for commands and more");
		
		if (UPlayer.get(player).getGroup() != PermissionGroup.DEFAULT){
			menu.setOption(8, new ItemStack(Material.DIAMOND), "Staff settings");
		}
	}

}
