package com.robinmc.ublisk.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.robinmc.ublisk.Main;
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
					player.sendPrefixedMessage("Guilds", "You joined " + guild.getName() + "!");
					return true;
				} else {
					// Player does not have an invite
					player.sendPrefixedMessage("Guilds", "You don't have any pending invites.");
					return true;
				}
			} else if (args[0].equalsIgnoreCase("leave")) {
				if (!player.isInGuild()) {
					player.sendPrefixedMessage("Guilds", "You are not in a guild");
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
			} else if (args[0].equalsIgnoreCase("help")){
				String[] usageLines = Main.getInstance().getCommand("guild").getUsage().split("\n");
				for (String usageLine : usageLines){
					player.sendMessage(ChatColor.AQUA + usageLine);
				}
				return true;
			} else {
				player.sendMessage(Message.WRONG_USAGE);
				return true;
			}
		} else if (args.length == 2) {
			if (args[0].equals("create")) {
				if (args[1].length() > 20) {
					player.sendPrefixedMessage("Guilds", "This guild name is too long.");
					return true;
				}

				if (player.isInGuild()) {
					player.sendPrefixedMessage("Guilds", "You are already in a guild. Please leave your current guild before creating a new one.");
					return true;
				}

				Guild guild = Guild.getGuild(args[1]);

				if (guild.exists()) {
					player.sendPrefixedMessage("Guilds", "A guild with this name already exists.");
					return true;
				}

				guild.create(player);
				
				player.sendPrefixedMessage("Guilds", "The guild has been created.");
				
				return true;
			} else if (args[0].equals("info")) {
				Guild guild = Guild.getGuild(args[1]);

				if (!guild.exists()) {
					player.sendPrefixedMessage("Guilds", "This guild does not exist.");
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
					player.sendPrefixedMessage("Guilds", "You are not in a guild.");
					return true;
				}

				UPlayer target = null;
				try {
					target = new UPlayer(args[1]);
				} catch (PlayerNotFoundException e) {
					player.sendMessage(Message.PLAYER_NOT_FOUND);
					return true;
				}

				Guild guild = player.getGuild();
				guild.invitePlayer(player, target);
				
				player.sendPrefixedMessage("Guilds", "You have invited " + target.getName() + " to your guild.");
				
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
