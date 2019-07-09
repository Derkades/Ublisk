package xyz.derkades.ublisk.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import xyz.derkades.ublisk.Message;
import xyz.derkades.ublisk.utils.UPlayer;

public class ReplyCommand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (!(sender instanceof Player)){
			sender.sendMessage(Message.NOT_A_PLAYER.toString());
			return true;
		}
		
		UPlayer player = new UPlayer(sender);
		
		UPlayer target = player.getLastSender();
		if (target == null){
			// TODO Last sender unknown message
		}
		
		String msg = String.join("", args);
		target.sendPrivateMessage(player, msg);
		return true;
		
	}

}
