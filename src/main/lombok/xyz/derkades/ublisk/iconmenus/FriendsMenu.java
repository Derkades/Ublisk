package xyz.derkades.ublisk.iconmenus;

import static net.md_5.bungee.api.ChatColor.BOLD;
import static net.md_5.bungee.api.ChatColor.GOLD;
import static net.md_5.bungee.api.ChatColor.GREEN;
import static net.md_5.bungee.api.ChatColor.RED;

import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import xyz.derkades.derkutils.bukkit.menu.OptionClickEvent;
import xyz.derkades.ublisk.Message;
import xyz.derkades.ublisk.utils.ItemBuilder;
import xyz.derkades.ublisk.utils.Menu;
import xyz.derkades.ublisk.utils.UPlayer;
import xyz.derkades.ublisk.utils.settings.Setting;

public class FriendsMenu extends Menu {

	public FriendsMenu(final UPlayer player) {
		super("Friends", 3*9, player);

		if (player.getFriends().isEmpty()){
			final ItemStack head = new ItemBuilder(player).name(GOLD + "You don't have any friends!").create();
			this.items.put(0, head);
		} else {
			int i = 0;
			for (final OfflinePlayer friend : player.getFriends()){
				final ItemStack head = new ItemBuilder(player)
						.name(ChatColor.DARK_AQUA + friend.getName())
						.lore(ChatColor.GRAY + "Click for more info")
						.create();

				this.items.put(i, head);

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

		final ItemStack friendsHealth = new ItemBuilder(Material.GLISTERING_MELON_SLICE).name(displayName).create();
		this.items.put(18, friendsHealth);
	}

	@Override
	public boolean onOptionClick(final OptionClickEvent event) {
		final UPlayer player = new UPlayer(event);
		final Material item = event.getItemStack().getType();

		if (item == Material.GLISTERING_MELON_SLICE){
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
			//OfflinePlayer friend = Ublisk.getOfflinePlayer(event.getName());
			final OfflinePlayer offlineFriend = player.getFriends().get(event.getPosition());

			if (offlineFriend == null) player.sendMessage("error");

			final UPlayer friend = new UPlayer(offlineFriend);

			player.sendMessage(
					friend.getDisplayName(ChatColor.DARK_AQUA, true),
					new ComponentBuilder(" - ")
							.color(ChatColor.DARK_GRAY)
							.bold(true)
							.create(),
					new ComponentBuilder("Remove friend")
							.color(ChatColor.RED)
							.italic(true)
							.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/friend remove " + friend.getName()))
							.create()
					);

			return true;
		}
	}

}
