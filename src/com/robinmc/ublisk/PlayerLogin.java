package com.robinmc.ublisk;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.robinmc.ublisk.utils.DataFile;
import com.robinmc.ublisk.utils.LocationUtils;
import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.Ublisk;

public class PlayerLogin implements Listener {

	/**
	 * List of names of players who are in the portal room
	 */
	public static final List<String> IN_PORTAL_ROOM = new ArrayList<String>();
	
	public static final List<String> TELEPORT_COOLDOWN = new ArrayList<String>();

	@EventHandler(priority = EventPriority.MONITOR)
	public void onQuit(PlayerQuitEvent event) {
		final UPlayer player = new UPlayer(event);
		
		if (IN_PORTAL_ROOM.contains(player.getName())){
			return;
		}
		
		Location location = player.getLocation();
		String locationString = LocationUtils.getStringFromLocation(location);
		DataFile.PLAYER_LOCATION.getConfig().set(player.getUniqueId().toString(), locationString);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onJoin(PlayerJoinEvent event) {
		final UPlayer player = new UPlayer(event);
		IN_PORTAL_ROOM.add(player.getName());
		
		Location teleportRoomLocation = new Location(Var.WORLD, 17.5, 74.5, -38.5, 90, 0);
		player.teleport(teleportRoomLocation);
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onMove(PlayerMoveEvent event){
		final UPlayer player = new UPlayer(event);
		
		if (TELEPORT_COOLDOWN.contains(player.getName())){
			return;
		}
		
		if (!isInRange(player)){
			return;
		}
		
		Block blockStandingOn = player.getLocation().getBlock().getRelative(BlockFace.DOWN);
		if (blockStandingOn.getType() == Material.BEDROCK){
			TELEPORT_COOLDOWN.add(player.getName());
			
			new BukkitRunnable(){
				public void run(){
					if (TELEPORT_COOLDOWN.contains(player.getName())){
						TELEPORT_COOLDOWN.remove(player.getName());
					}
				}
			}.runTaskLater(Main.getInstance(), 1*20);
			
			YamlConfiguration config = DataFile.PLAYER_LOCATION.getConfig();
			
			String locationString = config.getString(player.getUniqueId().toString());
			
			Location location;
			if (locationString == null){
				location = new Location(Var.WORLD, 1, 67, -2, 0, 0);
			} else {
				location = LocationUtils.getLocationFromString(locationString);
			}
			
			player.teleport(location);
			Ublisk.spawnParticle(Particle.EXPLOSION_NORMAL, location, 20, 0, 0, 0, 0.1);
			
			if (IN_PORTAL_ROOM.contains(player.getName())){
				IN_PORTAL_ROOM.remove(player.getName());
			}
		}
	}
	
	private static boolean isInRange(UPlayer player){
		final Location location = player.getLocation();
		double x = location.getX();
		double z = location.getZ();
		return x < 13 && x > 8 && z < -35 && z > -42;
	}

	public static class SavePlayerLocation extends BukkitRunnable {

		@Override
		public void run() {
			for (UPlayer player : Ublisk.getOnlinePlayers()) {
				if (IN_PORTAL_ROOM.contains(player.getName())) {
					return; // Do not save location if player is in portal room
				}

				Location location = player.getLocation();
				String locationString = LocationUtils.getStringFromLocation(location);
				DataFile.PLAYER_LOCATION.getConfig().set(player.getUniqueId().toString(), locationString);
			}
		}

	}

}
