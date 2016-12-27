package com.robinmc.ublisk.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.robinmc.ublisk.Message;
import com.robinmc.ublisk.utils.Logger;
import com.robinmc.ublisk.utils.Logger.LogLevel;
import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.exception.NotInGuildException;
import com.robinmc.ublisk.utils.exception.PlayerNotFoundException;
import com.robinmc.ublisk.utils.guilds.Guild;
import com.robinmc.ublisk.utils.guilds.Guilds;

public class GuildCommand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)){
			sender.sendMessage(Message.NOT_A_PLAYER.toString());
		}
		
		UPlayer player = new UPlayer(sender);
		
		if (args.length == 1){
			if (args[0].equalsIgnoreCase("leave")){
				try {
					Guild guild = player.getGuild();
					player.leaveGuild(guild);
					return true;
				} catch (NotInGuildException e) {
					player.sendMessage(Message.NOT_IN_GUILD);
					return true;
				}
			} else if (args[0].equalsIgnoreCase("accept")){
				if (player.isInGuild()){
					player.sendMessage(Message.ALREADY_IN_GUILD);
					return true;
				}
				
				try {
					Guild guild = player.getInvitedGuild();
					player.joinGuild(guild);
					player.sendMessage(Message.Complicated.Guilds.joinedGuild(guild));
					return true;
				} catch (NullPointerException e) {
					Logger.log(LogLevel.DEBUG, "GUILD IS NULL");
					player.sendMessage(Message.GUILD_NOT_INVITED);
					return true;
				} catch (Exception e){
					Logger.log(LogLevel.DEBUG, "A random exception occured, OH NO!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
					return true;
				}
			} else if (args[0].equalsIgnoreCase("list")){
				// TODO Color formatting, display guild points and members
				for (Guild guild : Guilds.getGuilds()){
					player.sendMessage(guild.getName());
				}
				return true;
			} else {
				player.sendMessage(Message.WRONG_USAGE);
				return true;
			}
		} else if (args.length == 2){
			if (args[0].equalsIgnoreCase("create")){
				if (player.isInGuild()){
					player.sendMessage(Message.ALREADY_IN_GUILD);
					return true;
				}
				
				if (!Guilds.getGuilds().contains(Guilds.fromName(args[1]))){
					player.joinGuild(Guilds.fromName(args[1]));
				} else {
					player.sendMessage(Message.GUILD_ALREADY_EXISTS);
				}
				return true;
			} else if (args[0].equalsIgnoreCase("invite")){
				UPlayer target;
				
				Logger.log(LogLevel.DEBUG, "/guild invite command yes very much hello there");
				
				try {
					target = new UPlayer(args[1]);
				} catch (PlayerNotFoundException e) {
					player.sendMessage(Message.PLAYER_NOT_FOUND);
					return true;
				}
				
				try {
					player.inviteToGuild(player.getGuild(), target);
				} catch (NotInGuildException e) {
					player.sendMessage(Message.NOT_IN_GUILD);
				}
				return true;
			} else if (args[0].equalsIgnoreCase("image") || args[0].equalsIgnoreCase("picture") || args[0].equalsIgnoreCase("icon")){
				if (!(
						args[1].startsWith("http://images.robinmc.com/ublisk-guild/icon/") &&
							(
								args[1].endsWith(".jpg") ||
								args[1].endsWith(".jpeg") ||
								args[1].endsWith(".png") ||
								args[1].endsWith(".gif")
							)
						)){
					player.sendMessage(Message.INVALID_GUILD_IMAGE_URL);
					return true;
				}
				
				Guild guild;
				
				try {
					guild = player.getGuild();
				} catch (NotInGuildException e) {
					player.sendMessage(Message.NOT_IN_GUILD);
					return true;
				}
				
				guild.setImageURL(args[1]);
				player.sendMessage(Message.GUILD_IMAGE_SET);
				return true;
			} else {
				player.sendMessage(Message.WRONG_USAGE);
				return true;
			}
		} else {
			player.sendMessage(Message.WRONG_USAGE);
			return true;
		}
	}

}
