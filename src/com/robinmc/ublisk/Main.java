package com.robinmc.ublisk;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.robinmc.ublisk.enums.Command;
import com.robinmc.ublisk.enums.Loot;
import com.robinmc.ublisk.utils.Config;
import com.robinmc.ublisk.utils.Listeners;
import com.robinmc.ublisk.utils.mob.Mob;
import com.robinmc.ublisk.utils.scheduler.Tasks;
import com.robinmc.ublisk.utils.sql.MySQL;
import com.robinmc.ublisk.utils.third_party.Lag;
import com.robinmc.ublisk.utils.variable.Var;

public class Main extends JavaPlugin {
	
	// Todo area
	// XXX /info [player] shows player info
	
	public static Main instance;
	
	@Override
	public void onEnable(){
		instance = this;
		
		Listeners.register();
		
		int delay = Command.registerAll();
		
		Tasks.start(delay);
		Config.create();
		
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Lag(), 100L, 1L);
		
		HashMaps.resetAllPlayers();
		HashMaps.doublexp.put(HashMaps.placeHolder(), false);
		HashMaps.doubleExpTime.put(HashMaps.placeHolder(), Var.DOUBLE_XP_TIME);
		HashMaps.doubleExpCooldown.put(HashMaps.placeHolder(), false);
		HashMaps.doubleExpBarActive.put(HashMaps.placeHolder(), false);
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable(){
			public void run(){
				Loot.removeLoot();
			}
		}, 10*20);
		
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
