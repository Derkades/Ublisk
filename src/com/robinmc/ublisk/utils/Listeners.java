package com.robinmc.ublisk.utils;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.listeners.ServerListPing;
import com.robinmc.ublisk.listeners.Votifier;
import com.robinmc.ublisk.listeners.entity.EntityDamageByEntity;
import com.robinmc.ublisk.listeners.entity.EntityDeath;
import com.robinmc.ublisk.listeners.entity.EntityExplode;
import com.robinmc.ublisk.listeners.entity.mob.CreatureSpawn;
import com.robinmc.ublisk.listeners.entity.mob.EntityCombust;
import com.robinmc.ublisk.listeners.player.AsyncPlayerChat;
import com.robinmc.ublisk.listeners.player.BreakBlock;
import com.robinmc.ublisk.listeners.player.InventoryClick;
import com.robinmc.ublisk.listeners.player.InventoryClose;
import com.robinmc.ublisk.listeners.player.PlayerCommandPreprocess;
import com.robinmc.ublisk.listeners.player.PlayerInteract;
import com.robinmc.ublisk.listeners.player.PlayerInteractEntity;
import com.robinmc.ublisk.listeners.player.PlayerItemConsume;
import com.robinmc.ublisk.listeners.player.PlayerJoin;
import com.robinmc.ublisk.listeners.player.PlayerQuit;
import com.robinmc.ublisk.listeners.player.PlayerResourcePackStatus;
import com.robinmc.ublisk.listeners.player.SongEnd;
import com.robinmc.ublisk.utils.logging.LogLevel;
import com.robinmc.ublisk.utils.logging.Logger;

public class Listeners {
	
	public static void register(){
		Logger.log(LogLevel.INFO, "Listeners", "Registering listeners...");
		register(new AsyncPlayerChat());
		register(new BreakBlock());
		register(new CreatureSpawn());
		register(new EntityCombust());
		register(new EntityDamageByEntity());
		register(new EntityDeath());
		register(new EntityExplode());
		register(new InventoryClose());
		register(new InventoryClick());
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
