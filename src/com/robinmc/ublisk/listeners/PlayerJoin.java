package com.robinmc.ublisk.listeners;

import static org.bukkit.ChatColor.AQUA;
import static org.bukkit.ChatColor.BOLD;
import static org.bukkit.ChatColor.DARK_AQUA;
import static org.bukkit.ChatColor.RESET;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import com.robinmc.ublisk.HashMaps;
import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.Message;
import com.robinmc.ublisk.Town;
import com.robinmc.ublisk.Var;
import com.robinmc.ublisk.database.Tracker;
import com.robinmc.ublisk.ext.com.bobacadodl.imgmessage.ImageChar;
import com.robinmc.ublisk.utils.DataFile;
import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.UUIDUtils;
import com.robinmc.ublisk.utils.inventory.ItemBuilder;
import com.robinmc.ublisk.utils.perm.PermissionGroup;
import com.robinmc.ublisk.utils.settings.Setting;

public class PlayerJoin implements Listener {
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event){
		final UPlayer player = new UPlayer(event);
		String pn = player.getName();
		UUID uuid = player.getUniqueId();
		
		player.setCollidable(false);
		
		event.setJoinMessage(DARK_AQUA + "" + BOLD + pn + RESET + AQUA + " has joined");
		
		player.givePotionEffect(PotionEffectType.BLINDNESS, 1*20, 0);
		player.givePotionEffect(PotionEffectType.NIGHT_VISION, 1*20, 0);
		
		player.sendTitle(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Ublisk", ChatColor.YELLOW + "Welcome back, " + player.getName() + "!");
		
		player.sendMessage(Message.PACK_SENDING);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable(){ 
			//For some reason sending the pack has to be delayed, otherwise the client won't get the message
			public void run(){
				player.setResourcePack(Var.PACK_URL);
			}
		}, 1*20);
		
		//Sync UUID and player name information with database for use by statistics site
		UUIDUtils.save(player);
		
		HashMaps.addPlayerToMaps(player);
		
		if (player.getSetting(Setting.PLAY_MUSIC)){
			Town town = player.getTown();
			town.playThemeToPlayer(player);
		}
		
		player.tracker(Tracker.JOIN_COUNT);
        
        String ip = player.getPlayer().getAddress().toString();
        ip = ip.replace("/", "");
        DataFile.IP.getConfig().set("ip." + uuid, ip);
        
        player.refreshXP();
        
        //If the player is not a Builder, Moderator or Owner disable builder mode to prevent griefing
        PermissionGroup group = player.getGroup();
        if (!(	group == PermissionGroup.BUILDER ||
        		group == PermissionGroup.MODERATOR ||
        		group == PermissionGroup.OWNER
        		) &&
        		player.isInBuilderMode()){
        	player.setBuilderModeEnabled(false);
        }
        
        new ItemBuilder(Material.CHEST)
        		.setName(ChatColor.BLUE + "" + ChatColor.BOLD + "Menu")
        		.setItemInInventory(player, 7);
        		
        player.setAttribute(Attribute.GENERIC_ATTACK_SPEED, 1);
        
        new BukkitRunnable(){
        	public void run(){
    			final String[] fancyStrings = new String[]{
    					"                       ",
    					" a a aa  a   a aaa a a ",
    					" a a a a a   a a   a a ",
    					" a a aa  a   a aaa aa  ",
    					" a a a a a   a   a aa  ",
    					" aaa aa  aaa a aaa a a ",
    					"                       "
    			};
    			for (String string : fancyStrings){
    				player.sendMessage(string.replace("a", ChatColor.GREEN + "" + ChatColor.BOLD + ImageChar.DARK_SHADE.getChar()).replace(" ", ChatColor.GRAY + "" + ChatColor.BOLD + ImageChar.DARK_SHADE.getChar()));
    			}
        	}
        }.runTaskLater(Main.getInstance(), 4);
	}
}
