package com.robinmc.ublisk.listeners.player;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import com.robinmc.ublisk.HashMaps;
import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.Message;
import com.robinmc.ublisk.Music;
import com.robinmc.ublisk.Tracker;
import com.robinmc.ublisk.Var;
import com.robinmc.ublisk.iconmenus.ClassMenu;
import com.robinmc.ublisk.utils.DataFile;
import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.UUIDUtils;
import com.robinmc.ublisk.utils.Ublisk;
import com.robinmc.ublisk.utils.exception.NotSetException;
import com.robinmc.ublisk.utils.inventory.item.ItemBuilder;
import com.robinmc.ublisk.utils.perm.PermissionGroup;
import com.robinmc.ublisk.utils.settings.Setting;

public class PlayerJoin implements Listener {
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event){
		final UPlayer player = UPlayer.get(event);
		String pn = player.getName();
		UUID uuid = player.getUniqueId();
		
		Ublisk.sendConsoleCommand("scoreboard teams join all " + pn); //Join team "all". This team disables 1.9 collision TODO: Better solution
		
		event.setJoinMessage(Message.Complicated.JoinQuit.playerJoin(pn));
		
		player.sendMessage(Message.PACK_SENDING.get());
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable(){ 
			//For some reason sending the pack has to be delayed, otherwise the client won't get the message
			public void run(){
				//ResourcePackAPI.setResourcepack(player, Var.PACK_URL);
				player.setResourcePack(Var.PACK_URL);
			}
		}, 1*20);
		
		//Save player uuid and name for later use
		UUIDUtils.save(player.getPlayer()); //TODO Update method to use UPlayer instead
		
		HashMaps.addPlayerToMaps(player.getPlayer()); //TODO Update method to use UPlayer instead
		
		try {
			if (player.getSetting(Setting.PLAY_MUSIC)){
				Music.playSong(player.getPlayer(), player.getTown().getName().toLowerCase()); //TODO Update method to use UPlayer instead
			}
		} catch (NotSetException e) {
			Music.playSong(player.getPlayer(), player.getTown().getName().toLowerCase());
			player.setSetting(Setting.PLAY_MUSIC, true);
		}
		
		//Tracker.JOINED.add(player);
		player.tracker(Tracker.JOINED);
        
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable(){
        	public void run(){
        		ClassMenu.open(player.getPlayer()); //TODO Update method to use UPlayer instead
        	}
        }, 2*20);
        
        String ip = player.getPlayer().getAddress().toString();
        ip = ip.replace("/", "");
        DataFile.IP.set("ip." + uuid, ip);
        
        //Exp.refresh(player);
        player.refreshXP();
        
        //If the player is not a Builder, Moderator or Owner disable builder mode to prevent griefing
        PermissionGroup group = player.getGroup();
        if (!(	group == PermissionGroup.BUILDER ||
        		group == PermissionGroup.MODERATOR ||
        		group == PermissionGroup.OWNER
        		) &&
        		player.isInBuilderMode()){
        	//Helper.disableBuilderMode(player);
        	player.setBuilderModeEnabled(false);
        }
        
        /*
        PlayerInventory inv = player.getInventory();
        if (!inv.contains(Material.CHEST)){
        	Item item = new Item(Material.CHEST);
        	item.setName(ChatColor.RESET + "" + ChatColor.BLUE + "" + ChatColor.BOLD + "Menu");
        	inv.setItem(7, item.getItemStack());
        }*/
        
        ItemStack item = new ItemBuilder(Material.CHEST)
        		.setName(ChatColor.BLUE + "" + ChatColor.BOLD + "Menu")
        		.getItemStack();
        player.getInventory().add(item);
        		
        
        //player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(1);
        player.setAttribute(Attribute.GENERIC_ATTACK_SPEED, 1);
	}
}
