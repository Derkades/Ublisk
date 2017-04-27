package com.robinmc.ublisk.modules;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;

import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.Message;
import com.robinmc.ublisk.utils.Logger.LogLevel;
import com.robinmc.ublisk.utils.UPlayer;

public class ResourcePack extends UModule implements CommandExecutor {
	
	/**
	 * Direct URL to resource pack zip. Do not change unless you know what you are doing.
	 */
	public static final String RESOURCE_PACK_URL = "http://ut.robinmc.com/UbliskTextures55.zip";
	
	@Override
	public void onEnable(Main plugin){
		plugin.getCommand("pack").setExecutor(this);
		log(this, LogLevel.INFO, "Using URL: " + RESOURCE_PACK_URL);
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onJoin(PlayerJoinEvent event){
		final UPlayer player = new UPlayer(event);
		player.sendMessage(Message.PACK_SENDING);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable(){ 
			//For some reason sending the pack has to be delayed, otherwise the client won't get the message
			public void run(){
				player.setResourcePack(RESOURCE_PACK_URL);
			}
		}, 1*20);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)){
			sender.sendMessage(Message.NOT_A_PLAYER.toString());
			return true;
		}
		
		UPlayer player = new UPlayer(sender);
		
		if (args.length == 1 && args[0].equalsIgnoreCase("check")){
			player.displayMobAppearanceEffect();
			player.sendMessage(Message.PACK_CHECK);
			return true;
		} else if (args.length == 0){
			player.setResourcePack(RESOURCE_PACK_URL);
			return true;
		} else {
			player.sendMessage(Message.WRONG_USAGE);
			return true;
		}
	}

}
