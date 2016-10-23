package com.robinmc.ublisk.commands;

import org.bukkit.Bukkit;
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

public class FriendsCommand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player){
			UPlayer player = UPlayer.get(sender);
			if (args.length == 0){
				FriendsMenu.open(player.getPlayer());
			} else if (args.length == 2){
				if (args[0].equals("add")){
					Player newFriend = Bukkit.getPlayer(args[1]);
					if (player.addFriend(newFriend)){
						player.sendMessage(Message.Complicated.Friends.friendAdded(newFriend.getName()));
					} else {
						player.sendMessage(Message.FRIEND_OFFLINE.get());
					}
				} else if (args[0].equals("remove") || args[0].equals("delete")){
					try {
						OfflinePlayer friend = UUIDUtils.getOfflinePlayerFromName(args[1]);
						if (player.removeFriend(friend)){
							player.sendMessage(Message.Complicated.Friends.friendRemoved(friend.getName()));
						} else {
							player.sendMessage(Message.FRIEND_NOT_EXIST.get());
						}
					} catch (PlayerNotFoundException e){
						player.sendMessage(Message.PLAYER_NOT_FOUND.get());
					}
					// XXX Add ability to remove friend by index
				} else {
					player.sendMessage(Message.WRONG_USAGE.get());
				}
			} else {
				player.sendMessage(Message.WRONG_USAGE.get());
			}
		} else {
			sender.sendMessage(Message.NOT_A_PLAYER.get());
		}
		return true;
	}

}
