package com.robinmc.ublisk;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.robinmc.ublisk.commands.Menu;
import com.robinmc.ublisk.listeners.PlayerJoin;
import com.robinmc.ublisk.listeners.SongEnd;
import com.robinmc.ublisk.utils.Console;


public class Main extends JavaPlugin {
	
	public static Main instance;
	
	@Override
	public void onEnable(){
		instance = this;
		
		registerListeners();
		registerCommands();
		
		Tasks.start();
		
		createConfig();
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
	}
	
	private void registerCommands(){
		Console.sendMessage("[Ublisk] Registering commands...");
		getCommand("menu").setExecutor(new Menu());
	}
	
	public void createConfig(){
		try {
			File file = new File(getDataFolder(), "config.yml");
			if (!getDataFolder().exists())
				getDataFolder().mkdirs();
			if (!file.exists()){
				getLogger().info("Config.yml not found, creating!");
				saveConfig();
			} else {
				getLogger().info("Config.yml found, loading!");
			}
		} catch (Exception e){
			System.out.println("Config already exists, a new one was not created!");
		}
	}
}
