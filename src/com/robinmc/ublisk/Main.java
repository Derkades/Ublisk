package com.robinmc.ublisk;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.robinmc.ublisk.commands.Credits;
import com.robinmc.ublisk.commands.Debug;
import com.robinmc.ublisk.commands.Help;
import com.robinmc.ublisk.commands.Menu;
import com.robinmc.ublisk.listeners.EntityDeath;
import com.robinmc.ublisk.listeners.EntityExplode;
import com.robinmc.ublisk.listeners.NPCClick;
import com.robinmc.ublisk.listeners.PlayerInteractEntity;
import com.robinmc.ublisk.listeners.PlayerItemConsume;
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
		pman.registerEvents(new EntityDeath(), this);
		pman.registerEvents(new EntityExplode(), this);
		pman.registerEvents(new NPCClick(), this);
		pman.registerEvents(new PlayerInteractEntity(), this);
		pman.registerEvents(new PlayerItemConsume(), this);
		pman.registerEvents(new PlayerJoin(), this);
		pman.registerEvents(new SongEnd(), this);
		
	}
	
	private void registerCommands(){
		Console.sendMessage("[Ublisk] Registering commands...");
		getCommand("credits").setExecutor(new Credits());
		getCommand("debug").setExecutor(new Debug());
		getCommand("help").setExecutor(new Help());
		getCommand("menu").setExecutor(new Menu());
		getCommand("music").setExecutor(new com.robinmc.ublisk.commands.Music());
	}
	
	@SuppressWarnings("deprecation")
	public static void tempBan(final Player player, final int time){
		player.setBanned(true);
		Console.sendMessage("[Banning] " + player.getName() + " has been banned for " + time + " seconds.");
		Bukkit.getScheduler().scheduleSyncDelayedTask(getInstance(), new Runnable(){
			public void run(){
				player.setBanned(false);
				Console.sendMessage("[Banning] " + player.getName() + " has been unbanned after " + time + " seconds.");
			}
		}, time * 20);
	}
	
	public static void removeMobs(){
		for (Entity entity: Var.world().getEntities()){
			if (entity.getType() == EntityType.CHICKEN || entity.getType() == EntityType.SHEEP){
				entity.remove();
			}
		}
	}
}
