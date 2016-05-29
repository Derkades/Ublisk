package com.robinmc.ublisk;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.robinmc.ublisk.commands.Menu;
import com.robinmc.ublisk.listeners.PlayerJoin;
import com.robinmc.ublisk.utils.Console;


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
		Console.sendMessage("Registering listeners...");
		PluginManager pman = Bukkit.getServer().getPluginManager();
		pman.registerEvents(new PlayerJoin(), this);
	}
	
	private void registerCommands(){
		Console.sendMessage("Registering commands...");
		getCommand("menu").setExecutor(new Menu());
	}
}
