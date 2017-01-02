package com.robinmc.ublisk.commands;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.robinmc.ublisk.Message;
import com.robinmc.ublisk.iconmenus.FriendsMenu;
import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.UUIDUtils;
import com.robinmc.ublisk.utils.exception.PlayerNotFoundException;

import net.md_5.bungee.api.ChatColor;

public class FriendsCommand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player){
			UPlayer player = new UPlayer(sender);
			if (args.length == 0){
				FriendsMenu.open(player);
			} else if (args.length == 2){
				if (args[0].equals("add")){
					UPlayer friend;
					try {
						friend = new UPlayer(args[1]);
					} catch (PlayerNotFoundException e) {
						player.sendMessage(Message.FRIEND_OFFLINE);
						return true;
					}
					
					if (player.addFriend(friend)){
						player.sendMessage(Message.Complicated.Friends.friendAdded(friend.getName()));
					} else {
						player.sendPrefixedMessage("Friends", ChatColor.RED + friend.getName() + " is already in your friends list.");
					}
				} else if (args[0].equals("remove") || args[0].equals("delete")){
					try {
						OfflinePlayer friend = UUIDUtils.getOfflinePlayerFromName(args[1]);
						if (player.removeFriend(friend)){
							player.sendMessage(Message.Complicated.Friends.friendRemoved(friend.getName()));
						} else {
							player.sendMessage(Message.FRIEND_NOT_EXIST);
						}
					} catch (PlayerNotFoundException e){
						player.sendMessage(Message.PLAYER_NOT_FOUND);
					}
					// XXX Add ability to remove friend by index
				} else {
					player.sendMessage(Message.WRONG_USAGE);
				}
			} else {
				player.sendMessage(Message.WRONG_USAGE);
			}
		} else {
			sender.sendMessage(Message.NOT_A_PLAYER.toString());
		}
		return true;
	}

}
