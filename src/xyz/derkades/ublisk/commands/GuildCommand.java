package xyz.derkades.ublisk.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import xyz.derkades.derkutils.ListUtils;
import xyz.derkades.derkutils.StringUtils;
import xyz.derkades.ublisk.Message;
import xyz.derkades.ublisk.utils.Guild;
import xyz.derkades.ublisk.utils.Guild.GuildInvite;
import xyz.derkades.ublisk.utils.UPlayer;
import xyz.derkades.ublisk.utils.exception.PlayerNotFoundException;

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
					
					for (OfflinePlayer offlinePlayer : guild.getMembers()){
						if (offlinePlayer.isOnline()){
							UPlayer guildMember = new UPlayer(offlinePlayer);
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
				
				Guild guild = player.getGuild();
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

				for (Guild guild : Guild.getGuildsList(10)) {
					List<String> memberNames = new ArrayList<String>();

					for (OfflinePlayer member : guild.getMembers())
						memberNames.add(member.getName());

					player.sendMessage(ChatColor.YELLOW + guild.getName() + ChatColor.DARK_GRAY + " | " + ChatColor.RED
							+ guild.getPoints() + " Points" + ChatColor.DARK_GRAY + " | " + ChatColor.BLUE
							+ String.join(", ", memberNames));
				}

				return true;
			} else if (args[0].equalsIgnoreCase("help")){
				return false;
			} else if (args[0].equalsIgnoreCase("info")){
				Guild guild = player.getGuild();
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
				if (args[1].length() > 20) {
					player.sendPrefixedMessage("Guilds", "This guild name is too long.");
					return true;
				}
				
				if (!StringUtils.validateString(args[1])){
					player.sendPrefixedMessage("Guilds", "Guild names can only contain alphanumerical characters and underscores (a-z, A-Z, 0-9, _)");
					return true;
				}

				if (player.getGuild() != null) {
					player.sendPrefixedMessage("Guilds", "You are already in a guild. Please leave your current guild before creating a new one.");
					return true;
				}

				Guild guild = new Guild(args[1]);

				if (guild.exists()) {
					player.sendPrefixedMessage("Guilds", "A guild with this name already exists.");
					return true;
				}

				guild.create(player);
				
				player.sendPrefixedMessage("Guilds", "The guild has been created.");
				
				return true;
			} else if (args[0].equals("info")) {
				Guild guild = new Guild(args[1]);

				if (!guild.exists()) {
					player.sendPrefixedMessage("Guilds", "This guild does not exist.");
					return true;
				}

				List<String> messages = new ArrayList<String>();
				messages.add("Name: " + guild.getName());
				messages.add("Points: " + guild.getPoints());
				messages.add("Description: " + guild.getDescription());
				messages.add("Members:");

				for (OfflinePlayer member : guild.getMembers()) {
					messages.add("- " + member.getName());
				}

				for (String message : messages)
					player.sendMessage(message);

				return true;
			} else if (args[0].equalsIgnoreCase("invite")) {
				if (player.getGuild() == null) {
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
			} else if (args[0].equalsIgnoreCase("rename")){
				if (player.getGuild() == null){
					player.sendPrefixedMessage("Guilds", "You are not in a guild.");
					return true;
				}
				
				if (!StringUtils.validateString(args[1])){
					player.sendPrefixedMessage("Guilds", "Guild names can only contain alphanumerical characters and underscores (a-z, A-Z, 0-9, _).");
					return true;
				}
				
				Guild guild = new Guild(args[1]);
				
				if (guild.exists()){
					player.sendPrefixedMessage("Guilds", "A guild with this name already exists.");
					return true;
				}
				
				guild.rename(args[1]);
				player.sendPrefixedMessage("Guilds", "Your guild has been renamed to " + args[1]);
				return true;
			} else if (args[0].equalsIgnoreCase("owner")){
				if (player.getGuild() == null){
					player.sendPrefixedMessage("Guilds", "You are not in a guild.");
					return true;
				}
				
				UPlayer target;
				try {
					target = new UPlayer(args[1]);
				} catch (PlayerNotFoundException e) {
					player.sendPrefixedMessage("Guilds", "This player could not be found.");
					return true;
				}
				
				Guild guild = player.getGuild();
				
				if (!player.getGuild().getName().equals(target.getGuild().getName())){
					player.sendPrefixedMessage("Guilds", "You cannot transfer ownership to a player who is not in your guild.");
					return true;
				}
				
				guild.setOwner(target);
				
				player.sendPrefixedMessage("Guilds", target.getName() + " is now the owner of " + guild.getName());
				return true;
			} else {
				return false;
			}
		} else if (args[0].equalsIgnoreCase("description") || args[0].equalsIgnoreCase("desc")){
			String description = String.join(" ", ListUtils.removeFirstStringFromArray(args));
			
			if (description.length() > 100){
				player.sendPrefixedMessage("Guilds", "This description is too long.");
				return true;
			}
			
			if (description.length() < 6){
				player.sendPrefixedMessage("Guilds", "This description is too short.");
				return true;
			}
			
			Guild guild = player.getGuild();
			if (guild == null){
				player.sendPrefixedMessage("Guilds", "You are not in a guild.");
				return true;
			}
			
			guild.setDescription(description);
			player.sendPrefixedMessage("Guilds", "You have changed your guild's description to " + StringUtils.addDotIfNecessary(description));
			return true;
		} else {
			return false;
		}
	}

}
