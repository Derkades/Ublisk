package com.robinmc.ublisk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.robinmc.ublisk.utils.Logger.LogLevel;
import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.Ublisk;

public class AFK extends UModule implements CommandExecutor {

	/**
	 * Number of seconds after which a player will be automatically set as AFK.
	 */
	private static final int AFK_TIME = 60;

	private final BukkitRunnable timer = new BukkitRunnable(){
		
		@Override
		public void run() {
			for (UPlayer player : Ublisk.getOnlinePlayers()){
				
				if (!AFK_SECONDS.containsKey(player.getName())){
					log(AFK.this, LogLevel.WARNING, "Player " + player.getName() + " not in AFK_SECONDS hashmap.");
					resetHashMaps(player);
					return;
				}
				
				AFK_SECONDS.put(player.getName(), AFK_SECONDS.get(player.getName()) + 1);

				//If the player has not moved for longer than AFK_TIME, set the player as AFK.
				if (AFK_SECONDS.get(player.getName()) >= AFK_TIME){

					AFK_SECONDS.put(player.getName(), 0);

					if (!player.isAfk())
						player.setAfk(true);
				}
			}
		}
		
	};

	@Override
	protected void onEnable(Plugin plugin) {
		log(this, LogLevel.INFO, "Test!");

		timer.runTaskTimer(plugin, 0, 20);
	}

	@Override
	protected void onDisable() {
		for (UPlayer player : Ublisk.getOnlinePlayers()) {
			if (AFK.contains(player.getName())) {
				AFK.remove(player.getName());
			}
		}
		
		timer.cancel();
	}

	private static final List<String> AFK = new ArrayList<String>();
	private static final Map<String, Integer> AFK_SECONDS = new HashMap<>();

	private static void resetHashMaps(UPlayer player) {
		AFK_SECONDS.put(player.getName(), 0);
	}

	public static boolean isAfk(UPlayer player) {
		return AFK.contains(player.getName());
	}

	public static void setAfk(UPlayer player, boolean setAfk) {
		if (setAfk) {
			AFK.add(player.getName());
		} else {
			if (AFK.contains(player.getName()))
				AFK.remove(player.getName());
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			UPlayer player = new UPlayer(sender);

			if (player.isAfk())
				player.setAfk(false);
			else player.setAfk(true);

			return true;
		} else {
			sender.sendMessage(Message.NOT_A_PLAYER.toString());
			return true;
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onMove(PlayerMoveEvent event) {
		UPlayer player = new UPlayer(event);

		if (player.isAfk())
			player.setAfk(false);

		AFK_SECONDS.put(player.getName(), 0);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onJoin(PlayerJoinEvent event) {
		UPlayer player = new UPlayer(event);

		if (AFK.contains(player.getName())) {
			AFK.remove(player.getName());
		}
	}

}
