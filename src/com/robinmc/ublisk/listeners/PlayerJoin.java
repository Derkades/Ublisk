package com.robinmc.ublisk.listeners;

import static org.bukkit.ChatColor.AQUA;
import static org.bukkit.ChatColor.BOLD;
import static org.bukkit.ChatColor.DARK_AQUA;
import static org.bukkit.ChatColor.RESET;

import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import com.robinmc.ublisk.DataFile;
import com.robinmc.ublisk.HashMaps;
import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.Town;
import com.robinmc.ublisk.database.PlayerInfo;
import com.robinmc.ublisk.ext.com.bobacadodl.imgmessage.ImageChar;
import com.robinmc.ublisk.permission.Permission;
import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.settings.Setting;

public class PlayerJoin implements Listener {
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event){
		final UPlayer player = new UPlayer(event);
		String pn = player.getName();
		UUID uuid = player.getUniqueId();
		
		player.setCollidable(false);
		
		event.setJoinMessage(DARK_AQUA + "" + BOLD + pn + RESET + AQUA + " has joined");
		
		player.givePotionEffect(PotionEffectType.BLINDNESS, 1*20, 0);
		player.givePotionEffect(PotionEffectType.NIGHT_VISION, 1*20, 0);
		
		player.sendTitle(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Ublisk", ChatColor.YELLOW + "Welcome back, " + player.getName() + "!");
		
		HashMaps.addPlayerToMaps(player);
		
		if (player.getSetting(Setting.PLAY_MUSIC)){
			Town town = player.getTown();
			if (town != null)
				town.playThemeToPlayer(player);
		}
		
		player.tracker(PlayerInfo.JOIN_COUNT);

		String ip = player.getPlayer().getAddress().toString();
		ip = ip.replace("/", "");
		DataFile.IP.getConfig().set("ip." + uuid, ip);

		player.updateXPBar();

		// Disable builder mode if the player no longer has permission
		if (player.hasPermission(Permission.BUILDER_MODE)){
			player.setBuilderModeEnabled(false);
		}
		
		if (player.isInBuilderMode()){
			player.setGameMode(GameMode.CREATIVE);
		} else {
			player.setGameMode(GameMode.ADVENTURE);
		}
		
		player.setFlying(false);

		player.setAttribute(Attribute.GENERIC_ATTACK_SPEED, 1);

		new BukkitRunnable() {

			public void run() {
				player.sendSpacers(10);

				final String[] fancyStrings = new String[] {
						"                       ", 
						" # # ##  #   # ### # # ", 
						" # # # # #   # #   # # ",
						" # # ##  #   # ### ##  ", 
						" # # # # #   #   # ##  ", 
						" ### ##  ### # ### # # ",
						"                       "
				};
				for (String string : fancyStrings) {
					player.sendMessage(string
							.replace("#", ChatColor.AQUA + "" + ChatColor.BOLD + ImageChar.DARK_SHADE.getChar())
							.replace(" ", ChatColor.DARK_AQUA + "" + ChatColor.BOLD + ImageChar.DARK_SHADE.getChar()));
				}
				player.sendMessage(ChatColor.GRAY
						+ "Welcome to Ublisk! If you find any bugs, please report them using /bug [description].");
			}
		}.runTaskLater(Main.getInstance(), 4);
	}

}
