package xyz.derkades.ublisk.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import xyz.derkades.ublisk.Main;
import xyz.derkades.ublisk.ext.com.sethbling.blinghomingarrows.HomingArrowsListener;
import xyz.derkades.ublisk.mob.listeners.EntityCombust;
import xyz.derkades.ublisk.mob.listeners.EntityDamage;
import xyz.derkades.ublisk.mob.listeners.EntityDeath;
import xyz.derkades.ublisk.quest.NPCClickListener;
import xyz.derkades.ublisk.utils.Logger;
import xyz.derkades.ublisk.utils.Logger.LogLevel;
import xyz.derkades.ublisk.weapons.abilities.AbilityListener;

public class Listeners {
	
	public static void register(){
		Logger.log(LogLevel.INFO, "Listeners", "Registering listeners...");
		for (Enum listener : Enum.values()){
			listener.register();
		}
	}
	
	private enum Enum {
		
		BREAK_BLOCK(new BreakBlock()),
		CHUNK_LOAD(new ChunkLoad()),
		ENTITY_EXPLODE(new EntityExplode()),
		INV_CLICK(new InventoryClick()),
		PLAYER_COMMAND(new PlayerCommandPreprocess()),
		INTERACT(new PlayerInteract()),
		INTERACT_ENTITY(new PlayerInteractEntity()),
		CONSUME(new PlayerItemConsume()),
		JOIN(new PlayerJoin()),
		MOVE(new PlayerMove()),
		QUIT(new PlayerQuit()),
		PING_SERVER(new ServerListPing()),
		SONG_END(new SongEnd()),
		PLAYER_DEATH(new PlayerDeath()),
		DROP_ITEM(new PlayerDropItem()),
		
		ENTITY_COMBUST(new EntityCombust()),
		ENTITY_DEATH(new EntityDeath()),
		ENTITY_DAMAGE(new EntityDamage()),
		
		ABILITIES(new AbilityListener()),
		SETHBLING_HOMING_ARROWS(new HomingArrowsListener()),
		NPC_CLICK_LISTENER(new NPCClickListener()),
		
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
