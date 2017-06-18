package xyz.derkades.ublisk.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import xyz.derkades.ublisk.Message;
import xyz.derkades.ublisk.modules.FormatChat;
import xyz.derkades.ublisk.utils.UPlayer;
import xyz.derkades.ublisk.utils.exception.PlayerNotFoundException;
import xyz.derkades.ublisk.utils.settings.Setting;

public class MsgCommand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (!(sender instanceof Player)){
			sender.sendMessage(Message.NOT_A_PLAYER.toString());
			return true;
		}
		
		UPlayer player = new UPlayer(sender);
		
		//If player is muted don't send private message
		if (FormatChat.IS_MUTED.get(player.getUniqueId())){
			player.sendMessage(Message.CANT_PM_MUTED);
			return true;
		}
		
		if (args.length >= 2){
			UPlayer target;
			
			try {
				target = new UPlayer(args[0]);
			} catch (PlayerNotFoundException e1) {
				player.sendMessage(Message.PLAYER_NOT_FOUND);
				return true;
			}
			
			List<String> list = new ArrayList<String>();
			
			for (int x = 1; x < args.length; x++){
				list.add(args[x]);
			}
			
			String msg = String.join(" ", list);
			
			//Play a sound if the target player has enabled sounds.
			if (target.getSetting(Setting.PM_SOUND)){
				target.playSound(Sound.ENTITY_ITEM_PICKUP);
			}
			
			target.setLastSender(player);
			
			target.sendPrivateMessage(player, msg);
			return true;
		} else {
			player.sendMessage(Message.WRONG_USAGE);
			return true;
		}
		
	}

}
