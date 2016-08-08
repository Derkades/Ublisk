package com.robinmc.ublisk;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

import com.robinmc.ublisk.utils.Config;
import com.robinmc.ublisk.utils.Console;
import com.robinmc.ublisk.utils.Listeners;
import com.robinmc.ublisk.utils.enums.Command;
import com.robinmc.ublisk.utils.enums.Loot;
import com.robinmc.ublisk.utils.enums.Tasks;
import com.robinmc.ublisk.utils.quest.NPCUtils;
import com.robinmc.ublisk.utils.sql.MySQL;
import com.robinmc.ublisk.utils.variable.Var;

public class Main extends JavaPlugin {
	
	//Todo area
	// XXX /info [player] shows player info
	// TODO Message if player tries to send chat message while muted
	// TODO Cancel /msg if player is muted (not if soft muted)
	// TODO Add lifecrystal system
	
	public static Main instance;
	
	//public static Connection connection;
	
	@Override
	public void onEnable(){
		instance = this;
		
		Listeners.register();
		
		int delay = 30;
		Console.sendMessage("[Ublisk] Registering commands...");
		for (final Command cmd : Command.values()){
			delay = delay + 5;
			Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable(){
				public void run(){
					Console.sendMessage("[Ublisk] Registered command with class " + cmd.getExecutor().getClass().getSimpleName());
					String command = cmd.getCommand();
					CommandExecutor executor = cmd.getExecutor();
					getCommand(command).setExecutor(executor);
				}
			}, delay);
		}
		
		Tasks.start(delay);
		
		NPCUtils.createNPCRegistry();
		
		Config.create();
		
		HashMaps.resetAllPlayers();
		
		/*
		new NPCUtils().despawnAll();
		new NPCUtils().spawnAll();
		*/
		
		HashMaps.doublexp.put("hi", false);
		HashMaps.doubleExpTime.put("hi", Var.doubleExpTime());
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable(){
			public void run(){
				Loot.removeLoot();
			}
		}, 10*20);
	}
	
	@Override
	public void onDisable(){
		instance = null;
		MySQL.onDisable();
		/*
		try {
			if (connection != null && connection.isClosed()){
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		*/
	}
	
	public static Main getInstance(){
		return instance;
	}
	
	/*
	public synchronized static void openConnection(){
		try {
			connection = DriverManager.getConnection("jdbc:mysql://192.168.0.125:3306/ublisk", "ublisk", "UJpwZBuEpw5C8MUv");
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	*/
	
	/*
	public synchronized static void closeConnection(){
		try {
			connection.close();
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	*/	
	
}
