package xyz.derkades.ublisk.listeners;

import java.util.UUID;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import xyz.derkades.ublisk.database.PlayerInfo;
import xyz.derkades.ublisk.utils.UPlayer;

public class PlayerMove implements Listener {
	
	@EventHandler
	public void onMove(PlayerMoveEvent event){
		if (!event.getPlayer().isOnGround()){
			return;
		}
		
		if (!event.getFrom().getBlock().equals(event.getTo().getBlock())){
			UUID uuid = event.getPlayer().getUniqueId();
			PlayerInfo.BLOCKS_WALKED.put(uuid, PlayerInfo.BLOCKS_WALKED.get(uuid) + 1);
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void isMoving(PlayerMoveEvent event) {
		UPlayer.LAST_WALK_TIME.put(event.getPlayer().getUniqueId(), System.currentTimeMillis());
	}

}
