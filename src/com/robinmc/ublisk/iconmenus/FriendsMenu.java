package com.robinmc.ublisk.iconmenus;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.Message;
import com.robinmc.ublisk.utils.Console;
import com.robinmc.ublisk.utils.Friends;
import com.robinmc.ublisk.utils.IconMenu;
import com.robinmc.ublisk.utils.Setting;
import com.robinmc.ublisk.utils.IconMenu.OptionClickEvent;
import com.robinmc.ublisk.utils.UUIDUtils;

import de.tr7zw.itemnbtapi.NBTItem;
import net.md_5.bungee.api.ChatColor;

public class FriendsMenu {
	
	private static IconMenu menu = new IconMenu("Friends", 3*9, new IconMenu.OptionClickEventHandler(){

		@Override
		public void onOptionClick(OptionClickEvent event) {
			Player player = event.getPlayer();
			String name = event.getName();
			if (name.contains("friend's health")){
				if (Setting.FRIENDS_SHOW_HEALTH.get(player)){
					Setting.FRIENDS_SHOW_HEALTH.set(player, false);
					player.sendMessage("disabled");
				} else {
					Setting.FRIENDS_SHOW_HEALTH.set(player, true);
					player.sendMessage("enabled");
				}
			} else {
				player.sendMessage(Message.ERROR_MENU.get());
				//TODO: Action for clicking on a friend
			}
		}
	}, Main.getInstance());
	
	public static void open(Player player){
		Console.sendMessage("[Menus] FriendsMenu has been opened for " + player.getName());
		fillMenu(player);
		menu.open(player);
	}
	
	private static void fillMenu(Player player){
		int i = 0;
		for (String string : Friends.get(player)){
			String pn = UUIDUtils.getNameFromIdString(string);
			
			ItemStack head = new ItemStack(Material.SKULL_ITEM, 1);
			head.setDurability((short) 3); //Durability value 3 is to get a human head instead of a skeleton head
			NBTItem nbt = new NBTItem(head);
			nbt.setString("SkullOwner", pn); //Uses the friend's name as skull owner
			head = nbt.getItem();
			
			menu.setOption(i, head, pn);
			
			i++;
			
			if (i > 17){
				break;
			}	
		}
		
		String displayName = "error";
		
		if (Setting.FRIENDS_SHOW_HEALTH.get(player)){		
			displayName = ChatColor.GOLD + "Show friend's health: " + ChatColor.GREEN + ChatColor.BOLD + "Enabled";
		} else {
			displayName = ChatColor.GOLD + "Show friend's health: " + ChatColor.RED + ChatColor.BOLD + "Disabled";
		}
		
		ItemStack friendsHealth = new ItemStack(Material.SPECKLED_MELON, 1);
		menu.setOption(18, friendsHealth, displayName);
	}

}
