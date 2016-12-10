package com.robinmc.ublisk.listeners.player;

import static net.md_5.bungee.api.ChatColor.DARK_AQUA;
import static net.md_5.bungee.api.ChatColor.GOLD;

import org.bukkit.GameMode;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import com.robinmc.ublisk.HashMaps;
import com.robinmc.ublisk.Tracker;
import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.scheduler.Scheduler;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;

public class PlayerInteractEntity implements Listener {
	
	@EventHandler
	public void onPlayerInteractEntityEvent(PlayerInteractEntityEvent event) {
		
		if (event.isCancelled()){
			return;
		}
		
		Entity entity = event.getRightClicked();
		
		final UPlayer player = UPlayer.get(event);
		
		player.tracker(Tracker.RIGHT_CLICKED);
		
		if (entity instanceof ArmorStand && player.getGameMode() != GameMode.CREATIVE){
			event.setCancelled(true);
		}
		
		if (entity instanceof Player && player.isSneaking()){
			if (HashMaps.ENTITY_RIGHT_CLICK_COOLDOWN.get(player.getUniqueId())){
				return;
			}
			
			HashMaps.ENTITY_RIGHT_CLICK_COOLDOWN.put(player.getPlayer().getUniqueId(), true);
			UPlayer target = UPlayer.get(entity);
			
			BaseComponent[] stats = new ComponentBuilder("Click here to view statistics")
					.bold(true)
					.color(DARK_AQUA)
					.event(new HoverEvent(
							HoverEvent.Action.SHOW_TEXT,
							new ComponentBuilder("Click to open website").color(GOLD).create()))
					.event(new ClickEvent(
							ClickEvent.Action.OPEN_URL,
							"http://ublisk.robinmc.com/stats/player.php?player=" + target.getName()))
					.create();
			
			BaseComponent[] addAsFriend = new ComponentBuilder("Add as friend")
					.bold(true)
					.color(DARK_AQUA)
					.event(new HoverEvent(
							HoverEvent.Action.SHOW_TEXT,
							new ComponentBuilder("Click to run /friend add " + target.getName()).color(GOLD).create()))
					.event(new ClickEvent(
							ClickEvent.Action.RUN_COMMAND,
							"/friend add " + target.getName()))
					.create();
			
			// TODO Invite to guild clickable message
			
			player.sendSpacers(2);
			player.sendMessage(stats);
			player.sendMessage(addAsFriend);
			
			Scheduler.runTaskLater(5, new Runnable(){
				public void run(){
					HashMaps.ENTITY_RIGHT_CLICK_COOLDOWN.put(player.getPlayer().getUniqueId(), false);
				}
			});
		}

	}

}
