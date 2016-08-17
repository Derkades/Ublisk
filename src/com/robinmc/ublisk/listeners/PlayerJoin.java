package com.robinmc.ublisk.listeners;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.inventivetalent.rpapi.ResourcePackAPI;

import com.robinmc.ublisk.HashMaps;
import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.enums.Music;
import com.robinmc.ublisk.enums.Tracker;
import com.robinmc.ublisk.iconmenus.ClassMenu;
import com.robinmc.ublisk.utils.Config;
import com.robinmc.ublisk.utils.Console;
import com.robinmc.ublisk.utils.Exp;
import com.robinmc.ublisk.utils.UUIDUtils;
import com.robinmc.ublisk.utils.variable.CMessage;
import com.robinmc.ublisk.utils.variable.Message;
import com.robinmc.ublisk.utils.variable.Var;

public class PlayerJoin implements Listener {
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event){
		final Player player = event.getPlayer();
		String pn = player.getName();
		UUID uuid = player.getUniqueId();
		
		if (!player.isOp()){
			player.setGameMode(GameMode.ADVENTURE); //If player is not opped, set player gamemode to adventure
		}
		
		Console.sendCommand("scoreboard teams join all " + pn); //Join team "all". This team disables 1.9 collision
		
		event.setJoinMessage(CMessage.playerJoin(pn));
		
		player.sendMessage(Message.PACK_SENDING.get());
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable(){ 
			//For some reason sending the pack has to be delayed, otherwise the client won't get the message
			public void run(){
				ResourcePackAPI.setResourcepack(player, Var.packURL);
			}
		}, 1*20);
		
		//Save player uuid and name for later use
		UUIDUtils.save(player);
		
		HashMaps.addPlayerToMaps(player);
		
		if (Main.getInstance().getConfig().isSet("settings.music." + uuid)){
	        if (Config.getBoolean("settings.music." + uuid)){
	        	String town = Config.getString("last-town." + uuid);
 		        Music.playSong(player, town);
	        }
		} else {
			  String town = Config.getString("last-town." + uuid);
			  Config.set("settings.music." + uuid, true);
			  Music.playSong(player, town);
		}
		
		Tracker.JOINED.add(player);
        
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable(){
        	public void run(){
        		ClassMenu.open(player);
        	}
        }, 2*20);
        
        String ip = player.getAddress().toString();
        ip.replace("/", "");
        Config.set("data.ip." + uuid, ip);
        
        Exp.refresh(player);
	}
}
