package com.robinmc.ublisk.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.abilities.AbilityListener;
import com.robinmc.ublisk.listeners.player.AsyncPlayerChat;
import com.robinmc.ublisk.listeners.player.BreakBlock;
import com.robinmc.ublisk.listeners.player.InventoryClick;
import com.robinmc.ublisk.listeners.player.InventoryClose;
import com.robinmc.ublisk.listeners.player.PlayerCommandPreprocess;
import com.robinmc.ublisk.listeners.player.PlayerDeath;
import com.robinmc.ublisk.listeners.player.PlayerInteract;
import com.robinmc.ublisk.listeners.player.PlayerInteractEntity;
import com.robinmc.ublisk.listeners.player.PlayerItemConsume;
import com.robinmc.ublisk.listeners.player.PlayerJoin;
import com.robinmc.ublisk.listeners.player.PlayerMove;
import com.robinmc.ublisk.listeners.player.PlayerQuit;
import com.robinmc.ublisk.listeners.player.PlayerResourcePackStatus;
import com.robinmc.ublisk.listeners.player.SongEnd;
import com.robinmc.ublisk.mob.CreatureSpawn;
import com.robinmc.ublisk.mob.listeners.EntityCombust;
import com.robinmc.ublisk.mob.listeners.EntityDamageByEntity;
import com.robinmc.ublisk.mob.listeners.EntityDeath;
import com.robinmc.ublisk.quest.NPCClickListener;
import com.robinmc.ublisk.utils.logging.LogLevel;
import com.robinmc.ublisk.utils.logging.Logger;
import com.sethbling.blinghomingarrows.HomingArrowsListener;

public class Listeners {
	
	public static void register(){
		Logger.log(LogLevel.INFO, "Listeners", "Registering listeners...");
		for (Enum listener : Enum.values()){
			listener.register();
		}
	}
	
	private enum Enum {
		
		CHAT(new AsyncPlayerChat()),
		BREAK_BLOCK(new BreakBlock()),
		CREATURE_SPAWN(new CreatureSpawn()),
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
		PLAYER_MOVE(new PlayerMove()),
		PLAYER_DEATH(new PlayerDeath()),
		
		ENTITY_COMBUST(new EntityCombust()),
		ENTITY_DAMAGE(new EntityDamageByEntity()),
		ENTITY_DEATH(new EntityDeath()),
		
		ABILITIES(new AbilityListener()),
		
		SETHBLING_HOMING_ARROWS(new HomingArrowsListener()),
		
		NPC_CLICK_LISTENER(new NPCClickListener());
		
		private Listener listener;
		
		Enum(Listener listener){
			this.listener = listener;
		}
		
		public void register(){
			Bukkit.getServer().getPluginManager().registerEvents(listener, Main.getInstance());
			Logger.log(LogLevel.DEBUG, "Listeners", "Registered " + listener);
		}
		
	}

}
