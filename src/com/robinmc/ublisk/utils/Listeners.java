package com.robinmc.ublisk.utils;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.abilities.AbilityListener;
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
		for (Enum listener : Enum.values()){
			Logger.log(LogLevel.DEBUG, "Listeners", "Registered " + listener);
			listener.register();
		}
	}
	
	private enum Enum {
		
		CHAT(new AsyncPlayerChat()),
		BREAK_BLOCK(new BreakBlock()),
		CREATURE_SPAWN(new CreatureSpawn()),
		ENTITY_COMBUST(new EntityCombust()),
		ENTITY_DAMAGE(new EntityDamageByEntity()),
		ENTITY_DEATH(new EntityDeath()),
		ENTITY_EXPLODE(new EntityExplode()),
		INV_CLOSE(new InventoryClose()),
		INV_CLICK(new InventoryClick()),
		PLAYER_COMMAND(new PlayerCommandPreprocess()),
		INTERACT(new PlayerInteract()),
		INTERACT_ENTITY(new PlayerInteractEntity()),
		CONSUME(new PlayerItemConsume()),
		JOIN(new PlayerJoin()),
		QUIT(new PlayerQuit()),
		RESOURCE_PACK(new PlayerResourcePackStatus()),
		PING_SERVER(new ServerListPing()),
		SONG_END(new SongEnd()),
		VOTE(new Votifier()),
		
		ABILITIES(new AbilityListener());
		
		private Listener listener;
		
		Enum(Listener listener){
			this.listener = listener;
		}
		
		public void register(){
			Bukkit.getServer().getPluginManager().registerEvents(listener, Main.getInstance());
		}
		
	}

}
