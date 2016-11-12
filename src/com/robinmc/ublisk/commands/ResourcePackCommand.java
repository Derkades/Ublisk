package com.robinmc.ublisk.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.inventivetalent.rpapi.ResourcePackAPI;

import com.robinmc.ublisk.Message;
import com.robinmc.ublisk.Var;
import com.robinmc.ublisk.utils.Console;
import com.robinmc.ublisk.utils.UPlayer;

public class ResourcePackCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)){
			sender.sendMessage(Message.NOT_A_PLAYER.get());
			return true;
		}
		
		UPlayer player = UPlayer.get(sender);
		
		if (args.length == 1 && args[0].equalsIgnoreCase("check")){
			Console.sendCommand("execute " + player.getName() + " ~ ~ ~ particle mobappearance ~ ~ ~ 0 0 1 50");
			player.sendMessage(Message.PACK_CHECK);
			return true;
		} else if (args.length == 0){
			ResourcePackAPI.setResourcepack(player.getPlayer(), Var.PACK_URL);
			return true;
		} else {
			player.sendMessage(Message.WRONG_USAGE);
			return true;
		}
	}

}
