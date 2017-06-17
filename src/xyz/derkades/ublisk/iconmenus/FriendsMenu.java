package xyz.derkades.ublisk.iconmenus;

import static net.md_5.bungee.api.ChatColor.BOLD;
import static net.md_5.bungee.api.ChatColor.GOLD;
import static net.md_5.bungee.api.ChatColor.GREEN;
import static net.md_5.bungee.api.ChatColor.RED;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import xyz.derkades.derkutils.bukkit.ItemBuilder;
import xyz.derkades.ublisk.Message;
import xyz.derkades.ublisk.utils.Menu;
import xyz.derkades.ublisk.utils.UPlayer;
import xyz.derkades.ublisk.utils.Ublisk;
import xyz.derkades.ublisk.utils.inventory.Item;
import xyz.derkades.ublisk.utils.settings.Setting;

public class FriendsMenu extends Menu {

	public FriendsMenu(UPlayer player) {
		super("Friends", 3*9, player);
	}

	@Override
	public List<MenuItem> getMenuItems(Player bukkitPlayer) {
		List<MenuItem> list = new ArrayList<>();
		UPlayer player = new UPlayer(bukkitPlayer);
		
		if (player.getFriends().isEmpty()){
			ItemStack head = new ItemBuilder(player.getName()).create();
			list.add(new MenuItem(0, head, GOLD + "You don't have any friends!"));
		} else {
			int i = 0;
			for (OfflinePlayer friend : player.getFriends()){
				ItemStack head = new Item(player.getName()).getItemStack();
				
				list.add(new MenuItem(i, head, friend.getName()));
				
				i++;
				
				if (i > 17){
					break;
				}	
			}
		}
		
		String displayName;
		
		if (player.getSetting(Setting.FRIENDS_SHOW_HEALTH))
			 displayName = GOLD + "Show friend's health: " + GREEN + BOLD + "Enabled";
		else displayName = GOLD + "Show friend's health: " + RED + BOLD + "Disabled";
		
		ItemStack friendsHealth = new ItemStack(Material.SPECKLED_MELON, 1);
		list.add(new MenuItem(18, friendsHealth, displayName));
		
		return list;
	}

	@Override
	public boolean onOptionClick(OptionClickEvent event) {
		UPlayer player = new UPlayer(event);
		Material item = event.getItemStack().getType();

		if (item == Material.SPECKLED_MELON){
			if (player.getSetting(Setting.FRIENDS_SHOW_HEALTH)){
				player.setSetting(Setting.FRIENDS_SHOW_HEALTH, false);
				player.sendMessage(Message.FRIEND_HEALTH_DISABLED);
			} else {
				player.setSetting(Setting.FRIENDS_SHOW_HEALTH, true);
				player.sendMessage(Message.FRIEND_HEALTH_ENABLED);
			}
			return false;
		} else if (player.getFriends().isEmpty()){
			return false;
		} else { //Clicked item is a player
			OfflinePlayer friend = Ublisk.getOfflinePlayer(event.getName());
			
			if (friend == null) player.sendMessage("error");
			
			BaseComponent[] text = new ComponentBuilder("Click here")
					.bold(true)
					.color(ChatColor.DARK_AQUA)
					.event(new HoverEvent(
							HoverEvent.Action.SHOW_TEXT,
							new ComponentBuilder("Click to open website").color(GOLD).create()))
					.event(new ClickEvent(
							ClickEvent.Action.OPEN_URL,
							"http://ublisk.robinmc.com/stats/player.php?player=" + friend.getName()))
					.create();
			player.sendMessage(text);
			return true;
		}
	}

}
