package com.robinmc.ublisk.listeners;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import com.robinmc.ublisk.HashMaps;
import com.robinmc.ublisk.Message;
import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.Ublisk;
import com.robinmc.ublisk.utils.perm.Permission;

import net.md_5.bungee.api.ChatColor;

public class PlayerCommandPreprocess implements Listener {
	
	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent event){
		
		if (event.isCancelled()){
			return;
		}
		
		String cmd = event.getMessage();
		UPlayer sender = UPlayer.get(event);
		String pn = sender.getName();
		
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
		
		if (sender.getGameMode() == GameMode.SPECTATOR && cmd.equals("/gamemode 1")){
			return;
		}
		
		if (cmd.contains("gamemode") && !cmd.contains("3")){
			sender.sendMessage(ChatColor.AQUA + "Please use builder mode instead");
			event.setCancelled(true);
			return;
		}
		
		if (cmd.contains("gamerule")){
			sender.sendMessage("no no no.");
			event.setCancelled(true);
			return;
		}
		
		for (UPlayer player: Ublisk.getOnlinePlayers()){
			if (player.hasPermission(Permission.COMMANDLOG)){
				if (!(player.getName() == sender.getName())){
					if (!(HashMaps.DISABLE_COMMAND_LOG.get(player.getUniqueId()))){
						player.sendMessage(Message.Complicated.commandLog(pn, cmd));
					}
				}
			}
		}
	}

}
