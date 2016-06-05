package com.robinmc.ublisk;

import org.bukkit.plugin.java.JavaPlugin;

import com.robinmc.ublisk.utils.Commands;
import com.robinmc.ublisk.utils.Config;
import com.robinmc.ublisk.utils.Listeners;

public class Main extends JavaPlugin {
	
	public static Main instance;
	
	@Override
	public void onEnable(){
		instance = this;
		
		Listeners.register();
		Commands.register();
		
		Tasks.start();
		
		Config.create();
		
		HashMaps.resetAllPlayers();
		
		NPCs.spawnAll();
	}
	
	@Override
	public void onDisable(){
		instance = null;
	}
	
	public static Main getInstance(){
		return instance;
	}	
	
}
