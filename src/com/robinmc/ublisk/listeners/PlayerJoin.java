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
import com.robinmc.ublisk.iconmenus.ClassMenu;
import com.robinmc.ublisk.utils.Config;
import com.robinmc.ublisk.utils.Console;
import com.robinmc.ublisk.utils.Exp;
import com.robinmc.ublisk.utils.enums.Music;
import com.robinmc.ublisk.utils.enums.Tracker;
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
		
		//Cache player uuid and name for later use
		Config.set("uuid.uuid." + pn, uuid.toString());
		Config.set("uuid.name." + uuid, pn);
		
		HashMaps.addPlayerToMaps(player);
		
		/*
		final NPCUtils npcApi = new NPCUtils();
		npcApi.despawnAll();
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable(){
			public void run(){
				npcApi.spawnAll();;
			}
		}, 10)
		*/
		
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
		
		/*
        try {
        	MySQL.openConnection();
        	int logins = 0;
        	boolean containsPlayer = false;
        	
        	try {
    			PreparedStatement sql = MySQL.prepareStatement("SELECT * FROM `login_count` WHERE uuid=?;");
    			sql.setString(1, uuid.toString());
    			ResultSet resultSet = sql.executeQuery();
    			containsPlayer = resultSet.next();
    			
    			sql.close();
    			resultSet.close();
    		} catch (SQLException e){
    			e.printStackTrace();
    		} finally {
    			try {
    				MySQL.closeConnection();
    			} catch (SQLException e) {
    				e.printStackTrace();
    			}
    		}
        	
        	if (containsPlayer){
        		PreparedStatement sql = MySQL.prepareStatement("SELECT count FROM `login_count` WHERE uuid=?;");
        		sql.setString(1, uuid.toString());
        		
        		ResultSet result = sql.executeQuery();
        		result.next();
        		
        		logins = result.getInt("count");
        		
        		PreparedStatement newlogins = MySQL.prepareStatement("UPDATE `login_count` SET count=? WHERE uuid=?;");
        		newlogins.setInt(1, logins + 1);
        		newlogins.setString(2, uuid.toString());
        		newlogins.executeUpdate();
        		
        		PreparedStatement name = MySQL.prepareStatement("UPDATE `login_count` SET name=? where uuid=?;");
        		name.setString(1, player.getName());
        		name.setString(2, uuid.toString());
        		name.executeUpdate();
        		
        		name.close();
        		newlogins.close();
        		sql.close();
        		result.close();
        	} else {
        		PreparedStatement newplayer = MySQL.prepareStatement("INSERT INTO `login_count` values(?, 1, ?);");
        		newplayer.setString(1, uuid.toString());
        		newplayer.setString(2, player.getName());
        		newplayer.execute();
        		newplayer.close();
        	}
        } catch (Exception e){
        	e.printStackTrace();
        } finally {
        	try {
				MySQL.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
        }
        */
		
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
