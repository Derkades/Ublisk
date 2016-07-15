package com.robinmc.ublisk.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.robinmc.ublisk.CMessage;
import com.robinmc.ublisk.Message;
import com.robinmc.ublisk.iconmenus.FriendsMenu;
import com.robinmc.ublisk.utils.Friends;
import com.robinmc.ublisk.utils.UUIDUtils;

public class FriendsCommand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player){
			Player player = (Player) sender;
			if (args.length == 0){
				FriendsMenu.open(player);
			} else if (args.length == 2){
				if (args[0].equals("add")){
					Player newFriend = Bukkit.getPlayer(args[1]);
					if (Friends.addFriend(player, newFriend)){
						player.sendMessage(CMessage.friendAdded(newFriend.getName()));
					} else {
						player.sendMessage(Message.FRIEND_OFFLINE.get());
					}
				} else if (args[0].equals("remove") || args[0].equals("delete")){
					OfflinePlayer friend = UUIDUtils.getPlayerFromName(args[1]);
					if (Friends.removeFriend(player, friend)){
						player.sendMessage(CMessage.friendRemoved(friend.getName()));
					} else {
						player.sendMessage(Message.FRIEND_NOT_EXIST.get());
					}
					//TODO: Add ability to remove friend by index
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
