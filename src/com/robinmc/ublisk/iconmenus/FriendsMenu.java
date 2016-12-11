package com.robinmc.ublisk.iconmenus;

import static net.md_5.bungee.api.ChatColor.BOLD;
import static net.md_5.bungee.api.ChatColor.DARK_AQUA;
import static net.md_5.bungee.api.ChatColor.GOLD;
import static net.md_5.bungee.api.ChatColor.GREEN;
import static net.md_5.bungee.api.ChatColor.RED;

import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.robinmc.ublisk.Message;
import com.robinmc.ublisk.utils.IconMenu;
import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.UUIDUtils;
import com.robinmc.ublisk.utils.IconMenu.OptionClickEvent;
import com.robinmc.ublisk.utils.exception.NotSetException;
import com.robinmc.ublisk.utils.inventory.item.ItemBuilder;
import com.robinmc.ublisk.utils.settings.Setting;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;

public class FriendsMenu {
	
	private static IconMenu menu = new IconMenu("Friends", 3*9, new IconMenu.OptionClickEventHandler(){

		@Override
		public void onOptionClick(OptionClickEvent event) {
			UPlayer player = UPlayer.get(event);
			Material item = event.getItem().getType();
			OfflinePlayer friend = event.getFriend();
			if (item == Material.SPECKLED_MELON){
				event.setWillClose(false);
				try {
					if (player.getSetting(Setting.FRIENDS_SHOW_HEALTH)){
						player.setSetting(Setting.FRIENDS_SHOW_HEALTH, false);
						player.sendMessage(Message.FRIEND_HEALTH_DISABLED);
					} else {
						player.setSetting(Setting.FRIENDS_SHOW_HEALTH, true);
						player.sendMessage(Message.FRIEND_HEALTH_ENABLED);
					}
				} catch (NotSetException e) {
					player.setSetting(Setting.FRIENDS_SHOW_HEALTH, false);
					player.sendMessage(Message.FRIEND_HEALTH_DISABLED);
				}
			} else {
				BaseComponent[] text = new ComponentBuilder("Click here")
						.bold(true)
						.color(DARK_AQUA)
						.event(new HoverEvent(
								HoverEvent.Action.SHOW_TEXT,
								new ComponentBuilder("Click to open website").color(GOLD).create()))
						.event(new ClickEvent(
								ClickEvent.Action.OPEN_URL,
								"http://ublisk.robinmc.com/stats/player.php?player=" + friend.getName()))
						.create();
				player.sendMessage(text);
			}
		}
	});
	
	public static void open(Player player){
		fillMenu(player);
		menu.open(player);
	}
	
	private static void fillMenu(Player bukkitPlayer){
		
		UPlayer player = UPlayer.get(bukkitPlayer);
		
		if (player.getFriends().isEmpty()){
			ItemStack head = new ItemBuilder(Material.SKULL_ITEM)
					.setAmount(1)
					.setDamage(3) //Damage value 3 is to get a human head instead of a skeleton head
					.setSkullOwner(player.getName())
					.getItemStack();
			menu.setOption(0, head, GOLD + "You don't have any friends!");
		} else {
			addFriendsToMenu(player);
		}
		
		String displayName = "error";
		
		try {
			if (player.getSetting(Setting.FRIENDS_SHOW_HEALTH)){		
				displayName = GOLD + "Show friend's health: " + GREEN + BOLD + "Enabled";
			} else {
				displayName = GOLD + "Show friend's health: " + RED + BOLD + "Disabled";
			}
		} catch (NotSetException e) {
			displayName = GOLD + "Show friend's health: " + GREEN + BOLD + "Enabled";
		}
		
		ItemStack friendsHealth = new ItemStack(Material.SPECKLED_MELON, 1);
		menu.setOption(18, friendsHealth, displayName);
	}
	
	private static void addFriendsToMenu(UPlayer player){
		int i = 0;
		for (String string : player.getFriends()){
			String pn = UUIDUtils.getNameFromIdString(string);
			
			ItemStack head = new ItemBuilder(Material.SKULL_ITEM)
					.setAmount(1)
					.setDamage(3) //Damage value 3 is to get a human head instead of a skeleton head
					.setSkullOwner(pn)
					.getItemStack();
			
			menu.setOption(i, head, pn);
			
			i++;
			
			if (i > 17){
				break;
			}	
		}
	}

}
