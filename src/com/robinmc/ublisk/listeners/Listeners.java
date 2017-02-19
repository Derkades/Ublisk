package com.robinmc.ublisk.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.PlayerLoginRoom;
import com.robinmc.ublisk.ResourcePack;
import com.robinmc.ublisk.Voting;
import com.robinmc.ublisk.ext.com.sethbling.blinghomingarrows.HomingArrowsListener;
import com.robinmc.ublisk.mob.listeners.EntityCombust;
import com.robinmc.ublisk.mob.listeners.EntityDamageByEntity;
import com.robinmc.ublisk.mob.listeners.EntityDeath;
import com.robinmc.ublisk.quest.NPCClickListener;
import com.robinmc.ublisk.utils.Logger;
import com.robinmc.ublisk.utils.Logger.LogLevel;
import com.robinmc.ublisk.weapons.abilities.AbilityListener;

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
		ENTITY_EXPLODE(new EntityExplode()),
		INV_CLOSE(new InventoryClose()),
		INV_CLICK(new InventoryClick()),
		PLAYER_COMMAND(new PlayerCommandPreprocess()),
		INTERACT(new PlayerInteract()),
		INTERACT_ENTITY(new PlayerInteractEntity()),
		CONSUME(new PlayerItemConsume()),
		JOIN(new PlayerJoin()),
		QUIT(new PlayerQuit()),
		PING_SERVER(new ServerListPing()),
		SONG_END(new SongEnd()),
		PLAYER_MOVE(new PlayerMove()),
		PLAYER_DEATH(new PlayerDeath()),
		
		ENTITY_COMBUST(new EntityCombust()),
		ENTITY_DAMAGE(new EntityDamageByEntity()),
		ENTITY_DEATH(new EntityDeath()),
		
		ABILITIES(new AbilityListener()),
		SETHBLING_HOMING_ARROWS(new HomingArrowsListener()),
		NPC_CLICK_LISTENER(new NPCClickListener()),
		PLAYER_LOGIN(new PlayerLoginRoom()),
		RESOURCE_PACK(new ResourcePack()),
		VOTING(new Voting()),
		
		;
		
		private Listener listener;
		
		Enum(Listener listener){
			this.listener = listener;
		}
		
		public void register(){
			Bukkit.getServer().getPluginManager().registerEvents(listener, Main.getInstance());
			Logger.log(LogLevel.DEBUG, "Listeners", "Registered " + listener.getClass().getSimpleName());
		}
		
	}

}
