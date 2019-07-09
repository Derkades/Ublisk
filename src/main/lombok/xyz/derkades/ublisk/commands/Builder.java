package xyz.derkades.ublisk.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import xyz.derkades.ublisk.Message;
import xyz.derkades.ublisk.utils.UPlayer;

public class Builder implements CommandExecutor {

	@Override
	public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(Message.NOT_A_PLAYER.toString());
			return true;
		}

		if (args.length != 0) {
			sender.sendMessage(Message.WRONG_USAGE.toString());
			return true;
		}

		final UPlayer player = new UPlayer(sender);

		if (!player.hasPermission("ublisk.builder")) {
			player.sendMessage(Message.NO_PERMISSION);
			return true;
		}

		player.toggleBuilderMode();
		return true;
	}

}
