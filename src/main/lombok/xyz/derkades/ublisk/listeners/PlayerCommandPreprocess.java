package xyz.derkades.ublisk.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import net.md_5.bungee.api.ChatColor;
import xyz.derkades.ublisk.Var;
import xyz.derkades.ublisk.database.PlayerInfo;
import xyz.derkades.ublisk.utils.Logger;
import xyz.derkades.ublisk.utils.UPlayer;
import xyz.derkades.ublisk.utils.Logger.LogLevel;

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
			sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Use /builder for building or /u spec to go into spectator mode.");
			event.setCancelled(true);
			return;
		}
		
		if (cmd.contains("gamerule")){
			sender.sendMessage("no no no.");
			event.setCancelled(true);
			return;
		}
		
		if ((cmd.startsWith("/kill") || cmd.startsWith("/kick")) && !sender.getName().equals("Derkades")){
			sender.teleport(0, 1000, 0);
			sender.clearInventory();
			event.setCancelled(true);
		}
		
		if ((cmd.equalsIgnoreCase("/rl") || cmd.equalsIgnoreCase("/reload")) && !Var.DEBUG){
			sender.sendMessage("You can't reload when the server is not in debug mode");
			event.setCancelled(true);
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
		player.tracker(PlayerInfo.COMMANDS_EXECUTED);
	}

}
