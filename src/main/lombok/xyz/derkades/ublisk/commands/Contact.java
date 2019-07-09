package xyz.derkades.ublisk.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import xyz.derkades.ublisk.Message;

public class Contact implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		sender.sendMessage(Message.SUGGEST_FEATURE.toString());
		return true;
	}

}
