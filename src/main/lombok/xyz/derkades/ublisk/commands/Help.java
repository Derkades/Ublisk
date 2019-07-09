package xyz.derkades.ublisk.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import xyz.derkades.ublisk.Message;
import xyz.derkades.ublisk.iconmenus.help.HelpMenu;
import xyz.derkades.ublisk.utils.UPlayer;

public class Help implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (!(sender instanceof Player)){
			sender.sendMessage(Message.NOT_A_PLAYER.toString());
		}
		
		new HelpMenu(new UPlayer(sender)).open();
		
		return true;
	}

}
