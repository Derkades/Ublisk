package com.robinmc.ublisk;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.robinmc.ublisk.utils.UPlayer;

public class AFK implements CommandExecutor, Listener {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (sender instanceof Player){
			UPlayer player = new UPlayer(sender);

			if (player.isAfk())
				player.setAfk(false);
			else
				player.setAfk(true);
			
			return true;
		} else {
			sender.sendMessage(Message.NOT_A_PLAYER.toString());
			return true;
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onMove(PlayerMoveEvent event){
		UPlayer player = new UPlayer(event);
		
		player.resetAfkTimer();
		
		if (player.isAfk())
			player.setAfk(false);
		
		HashMaps.AFK_MINUTES.put(player.getUniqueId(), 0);
	}

}
