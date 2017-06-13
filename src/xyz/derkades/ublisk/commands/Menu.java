package xyz.derkades.ublisk.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import xyz.derkades.ublisk.Message;
import xyz.derkades.ublisk.iconmenus.MainMenu;
import xyz.derkades.ublisk.utils.UPlayer;

public class Menu implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player){
			UPlayer player = new UPlayer(sender);
			new MainMenu(player).open();
			return true;
		} else {
			sender.sendMessage(Message.NOT_A_PLAYER.toString());
			return true;
		}
	}
}
