package xyz.derkades.ublisk.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import xyz.derkades.derkutils.ListUtils;
import xyz.derkades.derkutils.StringUtils;
import xyz.derkades.ublisk.Message;
import xyz.derkades.ublisk.utils.Guild;
import xyz.derkades.ublisk.utils.Guild.GuildInvite;
import xyz.derkades.ublisk.utils.UPlayer;
import xyz.derkades.ublisk.utils.Ublisk;
import xyz.derkades.ublisk.utils.exception.PlayerNotFoundException;

public class GuildCommand implements CommandExecutor {

	@Override
	public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(Message.NOT_A_PLAYER.toString());
		}

		final UPlayer player = new UPlayer(sender);

		if (args.length == 1) {

			if (args[0].equalsIgnoreCase("accept")) {
				if (Guild.GUILD_INVITES.containsKey(player.getName())) {
					// Player has got an invite
					final GuildInvite invite = Guild.GUILD_INVITES.get(player.getName());
					final Guild guild = invite.getGuild();
					player.setGuild(guild);
					player.sendPrefixedMessage("Guilds", "You joined " + guild.getName() + "!");

					for (final UPlayer guildMember : guild.getMembers()){
						if (guildMember.isOnline() && guildMember.getUniqueId() != player.getUniqueId()){
							guildMember.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + player.getName() + " has joined your guild!");
						}
					}

					Guild.GUILD_INVITES.remove(player.getName());

					return true;
				} else {
					// Player does not have an invite
					player.sendPrefixedMessage("Guilds", "You don't have any pending invites.");
					return true;
				}
			} else if (args[0].equalsIgnoreCase("leave")) {
				if (player.getGuild() == null) {
					player.sendPrefixedMessage("Guilds", "You are not in a guild");
					return true;
				}

				final Guild guild = player.getGuild();
				if (guild.getMembers().size() <= 1){ //If player is last person in guild (player can be owner if they're the only one left)
					player.sendPrefixedMessage("Guilds", "You left " + guild.getName() + ". Because you were the last player in the guild, your guild has been queued for deletion. If you regret leaving this guild, ask a staff member to add you back.");
					player.leaveGuild();
					return true;
				} else if (guild.getOwner().getUniqueId().equals(player.getUniqueId())){ //If player is owner - don't allow them to leave
					player.sendMessage("You are owner of this guild. You can only leave this guild if you transfer ownership to another guild member.");
					return true;
				} else { //Otherwise it's just a normal player - allow leaving
					player.sendPrefixedMessage("Guilds", "You have left " + guild.getName());
					player.leaveGuild();
					return true;
				}
			} else if (args[0].equalsIgnoreCase("list")) {
				player.sendMessage(ChatColor.AQUA + "----------={" + ChatColor.DARK_AQUA + " Guilds " + ChatColor.AQUA
						+ "}=----------");

				for (final Guild guild : Guild.getGuildsList(10)) {
					final List<BaseComponent> components = new ArrayList<>();
					components.addAll(Arrays.asList(TextComponent.fromLegacyText(ChatColor.YELLOW + guild.getName() + ChatColor.DARK_GRAY + " | " + ChatColor.RED + guild.getPoints() + " Points" + ChatColor.DARK_GRAY + " | ")));
					for (final UPlayer member : guild.getMembers()){
						components.addAll(Arrays.asList(member.getDisplayName(ChatColor.BLUE, false)));
					}

					player.sendMessage(components.toArray(new BaseComponent[0]));
				}

				return true;
			} else if (args[0].equalsIgnoreCase("help")){
				return false;
			} else if (args[0].equalsIgnoreCase("info")){
				final Guild guild = player.getGuild();
				if (guild == null){
					player.sendPrefixedMessage("Guilds", "You are not in a guild. Use /guild info [guild] to check information about another guild.");
					return true;
				}

				player.executeCommand("guild info " + guild.getName());
				return true;
			} else {
				return false;
			}
		} else if (args.length == 2) {
			if (args[0].equals("create")) {
				Ublisk.runAsync(() -> {

					if (args[1].length() > 20) {
						Ublisk.runSync(() -> player.sendPrefixedMessage("Guilds", "This guild name is too long."));
						return;
					}

					if (!StringUtils.validateString(args[1])){
						Ublisk.runSync(() -> player.sendPrefixedMessage("Guilds", "Guild names can only contain alphanumerical characters and underscores (a-z, A-Z, 0-9, _)"));
						return;
					}

					if (player.getGuild() != null) {
						Ublisk.runSync(() -> player.sendPrefixedMessage("Guilds", "You are already in a guild. Please leave your current guild before creating a new one."));
						return;
					}

					final Guild guild = new Guild(args[1]);

					if (guild.exists()) {
						Ublisk.runSync(() -> player.sendPrefixedMessage("Guilds", "A guild with this name already exists."));
						return;
					}

					guild.create(player);

					Ublisk.runSync(() -> player.sendPrefixedMessage("Guilds", "The guild has been created."));

				});

				return true;
			} else if (args[0].equals("info")) {
				final Guild guild = new Guild(args[1]);

				if (!guild.exists()) {
					player.sendPrefixedMessage("Guilds", "This guild does not exist.");
					return true;
				}

				player.sendMessage(
						new ComponentBuilder("Name: ").color(ChatColor.DARK_AQUA).create(),
						new ComponentBuilder(guild.getName()).color(ChatColor.AQUA).create());
				player.sendMessage(
						new ComponentBuilder("Points: ").color(ChatColor.DARK_AQUA).create(),
						new ComponentBuilder(guild.getPoints() + "").color(ChatColor.AQUA).create());
				player.sendMessage(
						new ComponentBuilder("Description: ").color(ChatColor.DARK_AQUA).create(),
						new ComponentBuilder(guild.getDescription()).color(ChatColor.AQUA).create());
				player.sendMessage(
						new ComponentBuilder("Members: ").color(ChatColor.DARK_AQUA).create());

				for (final UPlayer member : guild.getMembers()){
					player.sendMessage(
							new ComponentBuilder("- ").color(ChatColor.DARK_GRAY).bold(true).create(),
							member.getDisplayName(ChatColor.AQUA, false));
				}

				return true;
			} else if (args[0].equalsIgnoreCase("invite")) {
				if (player.getGuild() == null) {
					player.sendPrefixedMessage("Guilds", "You are not in a guild.");
					return true;
				}

				UPlayer target = null;
				try {
					target = new UPlayer(args[1]);
				} catch (final PlayerNotFoundException e) {
					player.sendMessage(Message.PLAYER_NOT_FOUND);
					return true;
				}

				final Guild guild = player.getGuild();
				guild.invitePlayer(player, target);

				player.sendPrefixedMessage("Guilds", "You have invited " + target.getName() + " to your guild.");

				return true;
			} else if (args[0].equalsIgnoreCase("rename")){
				if (player.getGuild() == null){
					player.sendPrefixedMessage("Guilds", "You are not in a guild.");
					return true;
				}

				if (!StringUtils.validateString(args[1])){
					player.sendPrefixedMessage("Guilds", "Guild names can only contain alphanumerical characters and underscores (a-z, A-Z, 0-9, _).");
					return true;
				}

				final Guild guild = new Guild(args[1]);

				if (guild.exists()){
					player.sendPrefixedMessage("Guilds", "A guild with this name already exists.");
					return true;
				}

				player.sendPrefixedMessage("Guilds", "Your guild has been renamed to " + args[1]);

				Ublisk.runAsync(() -> {
					guild.rename(args[1]);
				});

				return true;
			} else if (args[0].equalsIgnoreCase("owner")){
				if (player.getGuild() == null){
					player.sendPrefixedMessage("Guilds", "You are not in a guild.");
					return true;
				}

				UPlayer target;
				try {
					target = new UPlayer(args[1]);
				} catch (final PlayerNotFoundException e) {
					player.sendPrefixedMessage("Guilds", "This player could not be found.");
					return true;
				}

				final Guild guild = player.getGuild();

				if (!player.getGuild().getName().equals(target.getGuild().getName())){
					player.sendPrefixedMessage("Guilds", "You cannot transfer ownership to a player who is not in your guild.");
					return true;
				}

				player.sendPrefixedMessage("Guilds", target.getName() + " is now the owner of " + guild.getName());

				Ublisk.runAsync(() -> {
					guild.setOwner(target);
				});

				return true;
			} else {
				return false;
			}
		} else if (args[0].equalsIgnoreCase("description") || args[0].equalsIgnoreCase("desc")){
			final String description = String.join(" ", ListUtils.removeFirstStringFromArray(args));

			if (description.length() > 100){
				player.sendPrefixedMessage("Guilds", "This description is too long.");
				return true;
			}

			if (description.length() < 6){
				player.sendPrefixedMessage("Guilds", "This description is too short.");
				return true;
			}

			final Guild guild = player.getGuild();
			if (guild == null){
				player.sendPrefixedMessage("Guilds", "You are not in a guild.");
				return true;
			}

			player.sendPrefixedMessage("Guilds", "You have changed your guild's description to " + StringUtils.addDotIfNecessary(description));

			Ublisk.runAsync(() -> {
				guild.setDescription(description);
			});

			return true;
		} else {
			return false;
		}
	}

}
