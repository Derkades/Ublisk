package com.robinmc.ublisk;

import org.bukkit.plugin.java.JavaPlugin;

import com.robinmc.ublisk.utils.Commands;
import com.robinmc.ublisk.utils.Config;
import com.robinmc.ublisk.utils.Listeners;

public class Main extends JavaPlugin {
	
	//Todo section:
	//TODO: /report (sends link to forums)
	//TODO: /suggest (sends link to forums)
	
	public static Main instance;
	
	@Override
	public void onEnable(){
		instance = this;
		
		Listeners.register();
		Commands.register();
		
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
	
}
