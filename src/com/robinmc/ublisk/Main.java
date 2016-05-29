package com.robinmc.ublisk;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.robinmc.ublisk.commands.Menu;
import com.robinmc.ublisk.listeners.PlayerInteractEntity;
import com.robinmc.ublisk.listeners.PlayerJoin;
import com.robinmc.ublisk.listeners.SongEnd;
import com.robinmc.ublisk.utils.Config;
import com.robinmc.ublisk.utils.Console;


public class Main extends JavaPlugin {
	
	public static Main instance;
	
	@Override
	public void onEnable(){
		instance = this;
		
		registerListeners();
		registerCommands();
		
		Tasks.start();
		
		Config.create();
	}
	
	@Override
	public void onDisable(){
		instance = null;
	}
	
	public static Main getInstance(){
		return instance;
	}
	
	private void registerListeners(){
		Console.sendMessage("[Ublisk] Registering listeners...");
		PluginManager pman = Bukkit.getServer().getPluginManager();
		pman.registerEvents(new PlayerJoin(), this);
		pman.registerEvents(new SongEnd(), this);
		pman.registerEvents(new PlayerInteractEntity(), this);
	}
	
	private void registerCommands(){
		Console.sendMessage("[Ublisk] Registering commands...");
		getCommand("menu").setExecutor(new Menu());
	}
}
