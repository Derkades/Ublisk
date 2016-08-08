package com.robinmc.ublisk;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.robinmc.ublisk.utils.Config;
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
	
	public static Main instance;
	
	//public static Connection connection;
	
	@Override
	public void onEnable(){
		instance = this;
		
		Listeners.register();
		
		int delay = Command.registerAll();
		
		Tasks.start(delay);
		
		NPCUtils.createNPCRegistry();
		
		Config.create();
		
		HashMaps.resetAllPlayers();
		
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
	}
	
	public static Main getInstance(){
		return instance;
	}
	
}
