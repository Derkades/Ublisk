package com.robinmc.ublisk.listeners;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.PlayerInventory;
import org.inventivetalent.rpapi.ResourcePackAPI;

import com.robinmc.ublisk.HashMaps;
import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.enums.Helper;
import com.robinmc.ublisk.enums.Music;
import com.robinmc.ublisk.enums.Tracker;
import com.robinmc.ublisk.iconmenus.ClassMenu;
import com.robinmc.ublisk.utils.Config;
import com.robinmc.ublisk.utils.Console;
import com.robinmc.ublisk.utils.Exp;
import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.UUIDUtils;
import com.robinmc.ublisk.utils.inventory.item.Item;
import com.robinmc.ublisk.utils.perm.PermissionGroup;
import com.robinmc.ublisk.utils.variable.Message;
import com.robinmc.ublisk.utils.variable.Var;

import net.md_5.bungee.api.ChatColor;

public class PlayerJoin implements Listener {
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event){
		final Player player = event.getPlayer();
		String pn = player.getName();
		UUID uuid = player.getUniqueId();
		
		Console.sendCommand("scoreboard teams join all " + pn); //Join team "all". This team disables 1.9 collision
		
		event.setJoinMessage(Message.Complicated.JoinQuit.playerJoin(pn));
		
		player.sendMessage(Message.PACK_SENDING.get());
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable(){ 
			//For some reason sending the pack has to be delayed, otherwise the client won't get the message
			public void run(){
				ResourcePackAPI.setResourcepack(player, Var.PACK_URL);
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
        
        //If the player is not a Builder, Moderator or Owner disable builder mode to prevent griefing
        UPlayer uPlayer = new UPlayer(player);
        PermissionGroup group = uPlayer.getGroup();
        if (!(	group == PermissionGroup.BUILDER ||
        		group == PermissionGroup.MODERATOR ||
        		group == PermissionGroup.OWNER
        		) &&
        		uPlayer.isInBuilderMode()){
        	Helper.disableBuilderMode(player);
        }
        
        PlayerInventory inv = player.getInventory();
        if (!inv.contains(Material.CHEST)){
        	Item item = new Item(Material.CHEST);
        	item.setName(ChatColor.RESET + "" + ChatColor.BLUE + "" + ChatColor.BOLD + "Menu");
        	inv.setItem(7, item.getItemStack());
        }
	}
}
