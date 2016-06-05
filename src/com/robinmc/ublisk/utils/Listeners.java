package com.robinmc.ublisk.utils;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.listeners.EntityDeath;
import com.robinmc.ublisk.listeners.EntityExplode;
import com.robinmc.ublisk.listeners.NPCInteract;
import com.robinmc.ublisk.listeners.PlayerInteractEntity;
import com.robinmc.ublisk.listeners.PlayerItemConsume;
import com.robinmc.ublisk.listeners.PlayerJoin;
import com.robinmc.ublisk.listeners.PlayerQuit;
import com.robinmc.ublisk.listeners.PlayerResourcePackStatus;
import com.robinmc.ublisk.listeners.SongEnd;

public class Listeners {
	
	public static void register(){
		Console.sendMessage("[Ublisk] Registering listeners...");
		registerListener(new EntityDeath());
		registerListener(new EntityExplode());
		registerListener(new NPCInteract());
		registerListener(new PlayerInteractEntity());
		registerListener(new PlayerItemConsume());
		registerListener(new PlayerJoin());
		registerListener(new PlayerQuit());
		registerListener(new PlayerResourcePackStatus());
		registerListener(new SongEnd());
		
	}
	
	private static void registerListener(Listener listener){
		Main plugin = Main.getInstance();
		PluginManager pman = Bukkit.getServer().getPluginManager();
		pman.registerEvents(listener, plugin);
	}

}
