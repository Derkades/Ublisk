package com.robinmc.ublisk.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import com.robinmc.ublisk.database.PlayerInfo2;
import com.robinmc.ublisk.utils.Logger;
import com.robinmc.ublisk.utils.Logger.LogLevel;
import com.robinmc.ublisk.utils.UPlayer;

import net.md_5.bungee.api.ChatColor;

public class PlayerCommandPreprocess implements Listener {
	
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onCommand(PlayerCommandPreprocessEvent event){
		String cmd = event.getMessage();
		UPlayer sender = new UPlayer(event);
		
		if (cmd.startsWith("/time set")){
			sender.sendMessage("Please do not use /time set. The command has been cancelled");
			event.setCancelled(true);
			return;
		}
		
		if (cmd.length() >= 4){
			if (cmd.substring(0, 4).equalsIgnoreCase("/op ")){
				sender.sendMessage(ChatColor.AQUA + "oygnuonaiysgdcoauisgcdoauighscdoauismh");
				event.setCancelled(true);
				return;
			}
		}
		
		if (cmd.contains("gamemode")){
			sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + 
					"WARNING: Please don't use /gamemode. Use /builder instead to keep survival and creative inventory seperated.");
			return;
		}
		
		if (cmd.contains("gamerule")){
			sender.sendMessage("no no no.");
			event.setCancelled(true);
			return;
		}
		
		if (cmd.startsWith("/reload") || cmd.startsWith("/rl")){
			sender.sendMessage("NEE!!!");
			event.setCancelled(true);
			return;
		}

	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = false)
	public void logCommand(PlayerCommandPreprocessEvent event){
		String playerName = event.getPlayer().getName();
		String command = event.getMessage();
		boolean isCancelled = event.isCancelled();
		Logger.log(LogLevel.INFO, "CommandLog", playerName + ": " + command + " (cancelled: " + isCancelled + ")");
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = false)
	public void tracker(PlayerCommandPreprocessEvent event){
		UPlayer player = new UPlayer(event);
		player.tracker(PlayerInfo2.COMMANDS_EXECUTED);
	}

}
