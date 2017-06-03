package com.robinmc.ublisk.modules;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.utils.DoubleXP;
import com.robinmc.ublisk.utils.Guild;
import com.robinmc.ublisk.utils.Logger;
import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.URunnable;
import com.robinmc.ublisk.utils.Ublisk;
import com.robinmc.ublisk.utils.Logger.LogLevel;
import com.robinmc.ublisk.utils.caching.Cache;

public class CustomXP extends UModule {
	
	private static final int INITIAL_XP = 100;
	private static final float XP_INCREMENT = 1.05f;
	
	private static final int LEVEL_LIMIT = 100;
	
	@Override
	public void onEnable(Main plugin){
		new LevelChangeTask().runTimer(5);
	}
	
	public static int getRequiredXP(int level){
		return (int) (Math.pow(XP_INCREMENT, level) * INITIAL_XP);
	}
	
	private static int getLevel(int xp){
		for (int level = LEVEL_LIMIT; level > 0; level--){
			int requiredXP = getRequiredXP(level);
			if (xp > requiredXP){
				return level;
			}
		}
		return 0;
	}
	
	public static int getLevel(Player player){
		int xp = getXP(player);
		return getLevel(xp);
	}
	
	public static void setXP(Player player, final int xp){
		Cache.removeCachedObject("xp:" + player.getUniqueId());
		
		new URunnable(){
			public void run(){
				Connection connection = null;
				PreparedStatement statement = null;
				try {
					connection = Ublisk.getNewDatabaseConnection(player.getName() + " set xp");
					statement = connection.prepareStatement("UPDATE player_info_2 SET xp=? WHERE uuid=?");
					statement.setInt(1, xp);
					statement.setString(2, player.getUniqueId().toString());
					statement.executeUpdate();
				} catch (SQLException e){
					e.printStackTrace();
				} finally {
					try {
						if (statement != null) statement.close();
						if (connection != null) connection.close();
					} catch (SQLException e){
						e.printStackTrace();
					}
				}
			}
		}.runAsync();
		
		Cache.addCachedObject("xp:" + player.getUniqueId(), xp, 1000);
	}
	
	public static int getXP(Player player){
		//Check for cached values first
		Object cache = Cache.getCachedObject("xp:" + player.getUniqueId());
		if (cache != null){
			return (int) cache;
		}
		
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		
		int xp = -1;
		try {
			connection = Ublisk.getNewDatabaseConnection(player.getName() + " set xp");
			statement = connection.prepareStatement("SELECT xp FROM player_info_2 WHERE uuid=?");
			statement.setString(1, player.getUniqueId().toString());
			result = statement.executeQuery();
			result.next();
			xp = result.getInt("xp");
		} catch (SQLException e){
			e.printStackTrace();
		} finally {
			try {
				if (statement != null) statement.close();
				if (connection != null) connection.close();
			} catch (SQLException e){
				e.printStackTrace();
			}
		}
		
		if (xp == -1){
			throw new RuntimeException();
		}
		
		//Set cached value
		Cache.addCachedObject("xp:" + player.getUniqueId(), xp, 1000);
		
		return xp;
	}
	
	public static void updateXPBar(Player player){
		int level = getLevel(player);
		
		player.setLevel(level);
		
		int zeroProgressXP = getRequiredXP(level);
		Logger.log(LogLevel.DEBUG, "zero progress XP: " + zeroProgressXP);
		int fullProgressXP = getRequiredXP(level + 1);
		Logger.log(LogLevel.DEBUG, "full progress xp: " + fullProgressXP);
		int currentXP = getXP(player);
		Logger.log(LogLevel.DEBUG, "currentXP: " + currentXP);
		float progress = (currentXP - zeroProgressXP) / (float) (fullProgressXP - currentXP);
		Logger.log(LogLevel.DEBUG, "progress: " + progress);
		player.setExp(progress);
	}
	
	private static void levelUp(UPlayer player){
		int level = player.getLevel();
		Bukkit.broadcastMessage("");
		Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "--------------------------------------------");
		Bukkit.broadcastMessage(ChatColor.AQUA + "" + ChatColor.BOLD + player.getName() + ChatColor.DARK_AQUA + ""
				+ ChatColor.BOLD + " has reached level " + player.getLevel() + "!");
		
		if (level > 5)
			Bukkit.broadcastMessage(
					ChatColor.BLUE + "To celebrate this double XP will be activated in 10 seconds, get ready!");
		
		Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "--------------------------------------------");

		if (level > 5) {
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {

				public void run() {
					DoubleXP.startDoubleXP(player);
				}
			}, 10 * 20);
		}
		
		Guild guild = player.getGuild();
		if (guild != null){
			guild.addPoints(1);
		}
	}
	
	private static class LevelChangeTask extends URunnable {
		
		private static final Map<UUID, Integer> PREVIOUS_LEVEL = new HashMap<>();
		
		@Override
		public void run(){
			for (UPlayer player : Ublisk.getOnlinePlayers()){
				if (!PREVIOUS_LEVEL.containsKey(player.getUniqueId())){
					PREVIOUS_LEVEL.put(player.getUniqueId(), player.getLevel());
				}
				
				if (player.getLevel() <= PREVIOUS_LEVEL.get(player.getUniqueId())){
					//If level is lower
					PREVIOUS_LEVEL.put(player.getUniqueId(), player.getLevel());
				} else {
					//If level is higher
					levelUp(player);
				}
			}
		}
		
	}

}
