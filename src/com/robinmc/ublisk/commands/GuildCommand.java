package com.robinmc.ublisk.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.robinmc.ublisk.Message;
import com.robinmc.ublisk.utils.Guild;
import com.robinmc.ublisk.utils.Guild.GuildInvite;
import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.exception.PlayerNotFoundException;

import net.md_5.bungee.api.ChatColor;

public class GuildCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(Message.NOT_A_PLAYER.toString());
		}

		UPlayer player = new UPlayer(sender);

		if (args.length == 1) {

			if (args[0].equalsIgnoreCase("accept")) {
				if (Guild.GUILD_INVITES.containsKey(player.getName())) {
					// Player has got an invite
					GuildInvite invite = Guild.GUILD_INVITES.get(player.getName());
					Guild guild = invite.getGuild();
					player.setGuild(guild);
					player.sendMessage("You joined " + guild.getName() + "!"); // FIXME Fancy message
					return true;
				} else {
					// Player does not have an invite
					player.sendMessage("No invites"); // FIXME Fancy message
					return true;
				}
			} else if (args[0].equalsIgnoreCase("leave")) {
				if (!player.isInGuild()) {
					player.sendMessage("You are not in a guild"); // FIXME Fancy message
				} else {
					player.leaveGuild();
				}
				return true;
			} else if (args[0].equalsIgnoreCase("list")) {
				player.sendMessage(ChatColor.AQUA + "----------={" + ChatColor.DARK_AQUA + " Guilds " + ChatColor.AQUA
						+ "}=----------");

				for (Guild guild : Guild.getGuildsList()) {
					List<String> memberNames = new ArrayList<String>();

					for (OfflinePlayer member : guild.getMembers())
						memberNames.add(member.getName());

					player.sendMessage(ChatColor.YELLOW + guild.getName() + ChatColor.DARK_GRAY + " | " + ChatColor.RED
							+ guild.getPoints() + " Points" + ChatColor.DARK_GRAY + " | " + ChatColor.BLUE
							+ String.join(", ", memberNames));
				}

				return true;
			} else {
				player.sendMessage(Message.WRONG_USAGE);
				return true;
			}
		} else if (args.length == 2) {
			if (args[0].equals("create")) {
				if (args[1].length() > 20) {
					player.sendMessage("Guild name too long"); // FIXME Fancy message
					return true;
				}

				if (player.isInGuild()) {
					player.sendMessage("Already in guild"); // FIXME Fancy message
					return true;
				}

				Guild guild = Guild.getGuild(args[1]);

				if (guild.exists()) {
					player.sendMessage("Guild exists"); // FIXME Fancy message
					return true;
				}

				guild.create(player);
				player.sendMessage("Guild created"); // FIXME Fancy message
				return true;
			} else if (args[0].equals("info")) {
				Guild guild = Guild.getGuild(args[1]);

				if (!guild.exists()) {
					player.sendMessage("This guild does note exist"); // FIXME Fancy message
					return true;
				}

				List<String> messages = new ArrayList<String>();
				messages.add("Name: " + guild.getName());
				messages.add("Points: " + guild.getPoints());
				messages.add("Members:");

				for (OfflinePlayer member : guild.getMembers()) {
					messages.add("- " + member.getName());
				}

				for (String message : messages)
					player.sendMessage(message);

				return true;
			} else if (args[0].equalsIgnoreCase("invite")) {
				if (!player.isInGuild()) {
					player.sendMessage("You are not in a guild");
					return true;
				}

				UPlayer target = null;
				try {
					target = new UPlayer(args[1]);
				} catch (PlayerNotFoundException e) {
					player.sendMessage("Player not found"); // FIXME Fancy message
					return true;
				}

				Guild guild = player.getGuild();
				guild.invitePlayer(player, target);
				player.sendMessage("You have invited" + target.getName() + " to your guild"); // FIXME Fancy message
				return true;
			} else {
				player.sendMessage(Message.WRONG_USAGE);
				return true;
			}
		} else {
			sender.sendMessage(Message.WRONG_USAGE.toString());
			return true;
		}
	}

}
