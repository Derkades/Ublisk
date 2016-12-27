package com.robinmc.ublisk.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.robinmc.ublisk.Message;
import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.perm.Permission;

public class Builder implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(Message.NOT_A_PLAYER.toString());
			return true;
		}

		if (args.length != 0) {
			sender.sendMessage(Message.WRONG_USAGE.toString());
			return true;
		}

		UPlayer player = new UPlayer(sender);

		if (!player.hasPermission(Permission.BUILDER_MODE)) {
			player.sendMessage(Message.NO_PERMISSION);
			return true;
		}

		player.toggleBuilderMode();
		return true;
	}

}
