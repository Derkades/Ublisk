package com.robinmc.ublisk.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.robinmc.ublisk.Messages;
import com.robinmc.ublisk.iconmenus.FriendsMenu;
import com.robinmc.ublisk.utils.Console;
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
					player.sendMessage(newFriend.getName());
					Friends.addFriend(player, newFriend);	
					Console.sendMessage("Friends.addFriend(" + player + "," + newFriend + ");");
				} else if (args[0].equals("remove") || args[0].equals("delete")){
					OfflinePlayer friend = UUIDUtils.getPlayerFromName(args[1]);
					Friends.removeFriend(player, friend);
					//TODO: Add ability to remove friend by index
				} else {
					player.sendMessage(Messages.wrongUsage());
				}
			} else {
				player.sendMessage(Messages.wrongUsage());
			}
		} else {
			sender.sendMessage(Messages.noPlayer());
		}
		return true;
	}

}
