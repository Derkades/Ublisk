package com.robinmc.ublisk.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.robinmc.ublisk.Message;
import com.robinmc.ublisk.Var;
import com.robinmc.ublisk.utils.UPlayer;

public class ResourcePackCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)){
			sender.sendMessage(Message.NOT_A_PLAYER.toString());
			return true;
		}
		
		UPlayer player = new UPlayer(sender);
		
		if (args.length == 1 && args[0].equalsIgnoreCase("check")){
			//Ublisk.spawnParticle(Particle.MOB_APPEARANCE, new Location(Var.WORLD, 0, 0, 0), 1, 0, 0, 0, 0);
			player.displayMobAppearanceEffect();
			player.sendMessage(Message.PACK_CHECK);
			return true;
		} else if (args.length == 0){
			player.setResourcePack(Var.PACK_URL);
			return true;
		} else {
			player.sendMessage(Message.WRONG_USAGE);
			return true;
		}
	}

}
