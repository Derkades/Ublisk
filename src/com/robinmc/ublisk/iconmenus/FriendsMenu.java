package com.robinmc.ublisk.iconmenus;

import static org.bukkit.ChatColor.BOLD;
import static org.bukkit.ChatColor.GOLD;
import static org.bukkit.ChatColor.GRAY;
import static org.bukkit.ChatColor.YELLOW;

import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.utils.Console;
import com.robinmc.ublisk.utils.Exp;
import com.robinmc.ublisk.utils.Friends;
import com.robinmc.ublisk.utils.UUIDUtils;
import com.robinmc.ublisk.utils.enums.Setting;
import com.robinmc.ublisk.utils.third_party.IconMenu;
import com.robinmc.ublisk.utils.third_party.IconMenu.OptionClickEvent;
import com.robinmc.ublisk.utils.variable.Message;

import net.md_5.bungee.api.ChatColor;

public class FriendsMenu {
	
	private static IconMenu menu = new IconMenu("Friends", 3*9, new IconMenu.OptionClickEventHandler(){

		@Override
		public void onOptionClick(OptionClickEvent event) {
			Player player = event.getPlayer();
			Material item = event.getItem().getType();
			OfflinePlayer friend = event.getFriend();
			if (item == Material.SPECKLED_MELON){
				if (Setting.FRIENDS_SHOW_HEALTH.put(player)){
					Setting.FRIENDS_SHOW_HEALTH.set(player, false);
					player.sendMessage(Message.FRIEND_HEALTH_DISABLED.get());
				} else {
					Setting.FRIENDS_SHOW_HEALTH.set(player, true);
					player.sendMessage(Message.FRIEND_HEALTH_ENABLED.get());
				}
			} else {
				player.sendMessage("");
				player.sendMessage(GOLD + "Information for your friend " + YELLOW + BOLD + friend.getName());
				player.sendMessage("");
				player.sendMessage(GOLD + "XP" + GRAY + ": " + YELLOW + Exp.get(friend));
				player.sendMessage(GOLD + "More info coming soon!");
			}
		}
	}, Main.getInstance());
	
	public static void open(Player player){
		Console.sendMessage("[Menus] FriendsMenu has been opened for " + player.getName());
		fillMenu(player);
		menu.open(player);
	}
	
	private static void fillMenu(Player player){
		
		if (Friends.get(player).isEmpty()){
			ItemStack head = new ItemStack(Material.SKULL_ITEM, 1);
			head.setDurability((short) 3); //Durability value 3 is to get a human head instead of a skeleton head
			SkullMeta meta = (SkullMeta) head.getItemMeta();
			meta.setOwner("RobinMC");
			head.setItemMeta(meta);
			menu.setOption(0, head, ChatColor.GOLD + "You don't have any friends!");
		} else {
			addFriendsToMenu(player);
		}
		
		String displayName = "error";
		
		if (Setting.FRIENDS_SHOW_HEALTH.put(player)){		
			displayName = ChatColor.GOLD + "Show friend's health: " + ChatColor.GREEN + ChatColor.BOLD + "Enabled";
		} else {
			displayName = ChatColor.GOLD + "Show friend's health: " + ChatColor.RED + ChatColor.BOLD + "Disabled";
		}
		
		ItemStack friendsHealth = new ItemStack(Material.SPECKLED_MELON, 1);
		menu.setOption(18, friendsHealth, displayName);
	}
	
	private static void addFriendsToMenu(Player player){
		int i = 0;
		for (String string : Friends.get(player)){
			String pn = UUIDUtils.getNameFromIdString(string);
			Console.sendMessage(player.getName() + "      "  + string + "            " + pn);
			
			ItemStack head = new ItemStack(Material.SKULL_ITEM, 1);
			head.setDurability((short) 3); //Durability value 3 is to get a human head instead of a skeleton head
			SkullMeta meta = (SkullMeta) head.getItemMeta();
			meta.setOwner(pn);
			head.setItemMeta(meta);
			
			menu.setOption(i, head, pn);
			
			i++;
			
			if (i > 17){
				break;
			}	
		}
	}

}
