package com.robinmc.ublisk.utils;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.listeners.AsyncPlayerChat;
import com.robinmc.ublisk.listeners.BreakBlock;
import com.robinmc.ublisk.listeners.CreatureSpawn;
import com.robinmc.ublisk.listeners.EntityDamageByEntity;
import com.robinmc.ublisk.listeners.EntityDeath;
import com.robinmc.ublisk.listeners.EntityExplode;
import com.robinmc.ublisk.listeners.InventoryMoveItem;
import com.robinmc.ublisk.listeners.PlayerCommandPreprocess;
import com.robinmc.ublisk.listeners.PlayerInteract;
import com.robinmc.ublisk.listeners.PlayerInteractEntity;
import com.robinmc.ublisk.listeners.PlayerItemConsume;
import com.robinmc.ublisk.listeners.PlayerJoin;
import com.robinmc.ublisk.listeners.PlayerQuit;
import com.robinmc.ublisk.listeners.PlayerResourcePackStatus;
import com.robinmc.ublisk.listeners.ServerListPing;
import com.robinmc.ublisk.listeners.SongEnd;
import com.robinmc.ublisk.listeners.Votifier;
import com.robinmc.ublisk.utils.logging.LogLevel;
import com.robinmc.ublisk.utils.logging.Logger;

public class Listeners {
	
	public static void register(){
		Logger.log(LogLevel.INFO, "Listeners", "Registering listeners...");
		register(new AsyncPlayerChat());
		register(new BreakBlock());
		register(new CreatureSpawn());
		register(new EntityDamageByEntity());
		register(new EntityDeath());
		register(new EntityExplode());
		register(new InventoryMoveItem());
		register(new PlayerCommandPreprocess());
		register(new PlayerInteract());
		register(new PlayerInteractEntity());
		register(new PlayerItemConsume());
		register(new PlayerJoin());
		register(new PlayerQuit());
		register(new PlayerResourcePackStatus());
		register(new ServerListPing());
		register(new SongEnd());
		register(new Votifier());
	}
	
	private static void register(Listener listener){
		Main plugin = Main.getInstance();
		PluginManager pman = Bukkit.getServer().getPluginManager();
		pman.registerEvents(listener, plugin);
	}

}
