package com.robinmc.ublisk.modules;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.utils.Logger;
import com.robinmc.ublisk.utils.Logger.LogLevel;

public abstract class UModule implements Listener {
	
	public static final UModule[] ALL_MODULES = {
			new AFK(),
			new CustomMOTD(),
			new AutoRestart(),
			new PlayerLoginRoom(),
			};
	
	private static final List<UModule> RUNNING_MODULES = new ArrayList<UModule>();
	
	public boolean isRunning(){
		return RUNNING_MODULES.contains(this);
	}
	
	public void initialize(){
		if (isRunning()){
			throw new UnsupportedOperationException("Module is already initialized and running.");
		}
		
		RUNNING_MODULES.add(this);
		
		onEnable(Main.getInstance());
		
		Bukkit.getServer().getPluginManager().registerEvents(this, Main.getInstance());
		
		Logger.log(LogLevel.DEBUG, "Initialized module " + this.getClass().getSimpleName());
	}
	
	public void terminate(){
		if (!isRunning()){
			throw new UnsupportedOperationException("Cannot terminate a module that is not running.");
		}
		
		RUNNING_MODULES.remove(this);
		
		onDisable();
	}
	
	void log(UModule module, LogLevel level, String message) {
		Logger.log(level, module.getClass().getSimpleName(), message);
	}
	
	void onEnable(Plugin plugin){
		
	}
	
	void onDisable(){
		
	}

}
