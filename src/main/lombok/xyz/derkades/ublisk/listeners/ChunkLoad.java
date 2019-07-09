package xyz.derkades.ublisk.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;

import xyz.derkades.ublisk.database.ServerInfo;
import xyz.derkades.ublisk.utils.Logger;
import xyz.derkades.ublisk.utils.Logger.LogLevel;

public class ChunkLoad implements Listener {
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onChunkLoad(ChunkLoadEvent event){
		ServerInfo.CHUNKS_LOADED++;
		
		if (event.isNewChunk()){
			Logger.log(LogLevel.WARNING, "A new chunk has been generated.");
		}
	}

}
