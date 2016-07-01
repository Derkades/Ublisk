package com.robinmc.ublisk;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.robinmc.ublisk.utils.Commands;
import com.robinmc.ublisk.utils.Config;
import com.robinmc.ublisk.utils.Listeners;

public class Main extends JavaPlugin {
	
	//Todo area
	//TODO: /friends
	//TODO: /friends xp [player]
	//TODO: /party
	//TODO: Shows party member's health in bossbar
	//TODO: /info [player] shows player info
	
	public static Main instance;
	
	public static Connection connection;
	
	@Override
	public void onEnable(){
		instance = this;
		
		Listeners.register();
		Commands.register();
		
		Tasks.start();
		
		Config.create();
		
		HashMaps.resetAllPlayers();
		
		NPCs.spawnAll();
		
		HashMaps.doublexp.put("hi", false);
		HashMaps.doublexptime.put("hi", Var.doubleExpTime());
	}
	
	@Override
	public void onDisable(){
		instance = null;
		try {
			if (connection != null && connection.isClosed()){
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized static void openConnection(){
		try {
			connection = DriverManager.getConnection("jdbc:mysql://192.168.0.125:3306/ublisk", "ublisk", "UJpwZBuEpw5C8MUv");
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public synchronized static void closeConnection(){
		try {
			connection.close();
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public synchronized static boolean playerDataContainsPlayer(Player player){
		try {
			PreparedStatement sql = connection.prepareStatement("SELECT * FROM `login_count` WHERE uuid=?;");
			sql.setString(1, player.getUniqueId().toString());
			ResultSet resultSet = sql.executeQuery();
			boolean containsPlayer = resultSet.next();
			
			sql.close();
			resultSet.close();
			
			return containsPlayer;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public static Main getInstance(){
		return instance;
	}	
	
}
