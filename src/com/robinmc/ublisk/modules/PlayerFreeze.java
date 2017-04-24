package com.robinmc.ublisk.modules;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerFreeze extends UModule {
	
	private static final List<String> FROZEN = new ArrayList<String>();
	
	public static void setFrozen(Player player, boolean frozen){
		if (frozen){
			if (!FROZEN.contains(player.getName())){
				FROZEN.add(player.getName());
			}
		} else{
			if (FROZEN.contains(player.getName())){
				FROZEN.remove(player.getName());
			}
		}
	}
	
	public static boolean isFrozen(Player player){
		return FROZEN.contains(player.getName());
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onMove(PlayerMoveEvent event){
		Player player = event.getPlayer();
		
		if (isFrozen(player)){
			Location from = event.getFrom();
			Location to = event.getTo();
			from.setYaw(to.getYaw());
			from.setPitch(to.getPitch());
			event.setTo(from);
		}
	}

}
