package xyz.derkades.ublisk.commands;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import xyz.derkades.ublisk.Message;
import xyz.derkades.ublisk.iconmenus.FriendsMenu;
import xyz.derkades.ublisk.utils.UPlayer;
import xyz.derkades.ublisk.utils.Ublisk;

public class FriendsCommand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player){
			UPlayer player = new UPlayer(sender);
			if (args.length == 0){
				FriendsMenu.open(player);
			} else if (args.length == 2){
				if (args[0].equals("add")){
					OfflinePlayer target = Ublisk.getOfflinePlayer(args[1]);
					
					if (target == null){
						player.sendMessage(Message.PLAYER_NOT_FOUND);
						return true;
					}
					
					if (player.isFriend(target)){
						player.sendPrefixedMessage("Friends", ChatColor.RED + target.getName() + " is already in your friends list.");
						return true;
					}
					
					player.addFriend(target);
					
					player.sendPrefixedMessage("Friends", target.getName() + " has been added to your friends list");
				} else if (args[0].equals("remove") || args[0].equals("delete")){
					OfflinePlayer target = Ublisk.getOfflinePlayer(args[1]);
					
					if (target == null){
						player.sendMessage(Message.PLAYER_NOT_FOUND);
						return true;
					}
					
					if (!player.isFriend(target)){
						player.sendMessage(Message.FRIEND_NOT_EXIST);
						return true;
					}
					
					player.removeFriend(target);
					player.sendMessage(target.getName() + " has been removed from your friends list");
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
