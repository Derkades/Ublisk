package com.robinmc.ublisk.listeners;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.inventivetalent.rpapi.ResourcePackAPI;

import com.robinmc.ublisk.HashMaps;
import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.Music;
import com.robinmc.ublisk.iconmenus.ClassMenu;
import com.robinmc.ublisk.utils.Config;
import com.robinmc.ublisk.utils.Console;
import com.robinmc.ublisk.utils.quest.QuestCharacter;
import com.robinmc.ublisk.utils.variable.CMessage;
import com.robinmc.ublisk.utils.variable.Message;
import com.robinmc.ublisk.utils.variable.Var;

public class PlayerJoin implements Listener {
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event){
		final Player player = event.getPlayer();
		String pn = player.getName();
		
		if (!player.isOp()){
			player.setGameMode(GameMode.ADVENTURE); //If player is not opped, set player gamemode to adventure
		}
		
		Console.sendCommand("scoreboard teams join all " + pn); //Join team "all". This team disables 1.9 collision
		
		event.setJoinMessage(CMessage.playerJoin(pn));
		
		player.sendMessage(Message.PACK_SENDING.get());
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable(){ 
			//For some reason sending the pack has to be delayed, otherwise the client won't get the message
			public void run(){
				ResourcePackAPI.setResourcepack(player, Var.pack());
			}
		}, 1*20);
		
		//Cache player uuid and name for later use
		Config.set("uuid.uuid." + pn, player.getUniqueId().toString());
		Config.set("uuid.name." + player.getUniqueId(), pn);
		
		HashMaps.addPlayerToMaps(player);
		
		QuestCharacter.despawnAll();
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable(){
			public void run(){
				QuestCharacter.spawnAll();
			}
		}, 10);
		
        try {
        	if (Config.getBoolean("settings.music." + player.getUniqueId())){
        		String town = Config.getString("last-town." + player.getUniqueId());
 		        Music.playSong(player, town);
        	}
        } catch (Exception e){
        	 String town = Config.getString("last-town." + player.getUniqueId());
		     Music.playSong(player, town);
        }
        
        Main.openConnection();
        try {
        	int logins = 0;
        	
        	if (Main.playerDataContainsPlayer(player)){
        		PreparedStatement sql = Main.connection.prepareStatement("SELECT count FROM `login_count` WHERE uuid=?;");
        		sql.setString(1, player.getUniqueId().toString());
        		
        		ResultSet result = sql.executeQuery();
        		result.next();
        		
        		logins = result.getInt("count");
        		
        		PreparedStatement newlogins = Main.connection.prepareStatement("UPDATE `login_count` SET count=? WHERE uuid=?;");
        		newlogins.setInt(1, logins + 1);
        		newlogins.setString(2, player.getUniqueId().toString());
        		newlogins.executeUpdate();
        		
        		PreparedStatement name = Main.connection.prepareStatement("UPDATE `login_count` SET name=? where uuid=?;");
        		name.setString(1, player.getName());
        		name.setString(2, player.getUniqueId().toString());
        		name.executeUpdate();
        		
        		name.close();
        		newlogins.close();
        		sql.close();
        		result.close();
        	} else {
        		PreparedStatement newplayer = Main.connection.prepareStatement("INSERT INTO `login_count` values(?, 1, ?);");
        		newplayer.setString(1, player.getUniqueId().toString());
        		newplayer.setString(2, player.getName());
        		newplayer.execute();
        		newplayer.close();
        	}
        } catch (Exception e){
        	e.printStackTrace();
        } finally {
        	Main.closeConnection();
        }
        
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable(){
        	public void run(){
        		ClassMenu.open(player);
        	}
        }, 2*20);
        
        
	}
	
}
