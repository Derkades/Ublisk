package com.robinmc.ublisk;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.robinmc.ublisk.commands.Menu;
import com.robinmc.ublisk.listeners.PlayerJoin;


public class Main extends JavaPlugin {
	
	public static Main instance;
	
	@Override
	public void onEnable(){
		instance = this;
		
		registerListeners();
		registerCommands();
		
		Tasks.start();
	}
	
	@Override
	public void onDisable(){
		instance = null;
	}
	
	public static Main getInstance(){
		return instance;
	}
	
	private void registerListeners(){
		PluginManager pman = Bukkit.getServer().getPluginManager();
		pman.registerEvents(new PlayerJoin(), this);
	}
	
	private void registerCommands(){
		getCommand("menu").setExecutor(new Menu());
	}
}
