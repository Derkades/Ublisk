package com.robinmc.ublisk;

import org.bukkit.plugin.java.JavaPlugin;

import com.robinmc.ublisk.commands.Command;
import com.robinmc.ublisk.listeners.Listeners;
import com.robinmc.ublisk.mob.Mob;
import com.robinmc.ublisk.utils.scheduler.Task;
import com.robinmc.ublisk.utils.sql.MySQL;

public class Main extends JavaPlugin {
	
	// Todo area
	// XXX /info [player] shows player info
	
	public static Main instance;
	
	@Override
	public void onEnable(){
		instance = this;
		
		Listeners.register();
		
		Command.registerAll();
		
		for (Task task : Task.values()) task.start();
		
		HashMaps.resetAllPlayers();
		
		Mob.startMobSpawning();
	}
	
	@Override
	public void onDisable(){
		instance = null;
		MySQL.onDisable();
	}
	
	public static Main getInstance(){
		return instance;
	}
	
}
