package com.robinmc.ublisk.enums;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.robinmc.ublisk.HashMaps;
import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.logging.LogLevel;
import com.robinmc.ublisk.utils.logging.Logger;
import com.robinmc.ublisk.utils.sql.MySQL;

public enum Tracker {
	
	/*
	 * Database layout:
	 * 'uuid' with type TEXT
	 * 'count' with type INT
	 * 'name' with type TEXT
	 */
	
	RIGHT_CLICKED(HashMaps.rightClicked, "right_click"),
	LEFT_CLICKED(HashMaps.leftClicked, "left_click"),
	JOINED(HashMaps.loggedIn, "login_count"),
	MOB_KILLS(HashMaps.mobKills, "mob_kills"),
	LOOT_FOUND(HashMaps.lootFound, "loot_found"),
	CHAT_MESSAGES(HashMaps.chatMessages, "chat_messages");
	
	private Map<UUID, Integer> map;
	private String table;
	
	Tracker(Map<UUID, Integer> map, String table){
		this.map = map;
		this.table = table;
	}
	
	public Map<UUID, Integer> getMap(){
		return map;
	}
	
	public String getTable(){
		return table;
	}
	
	public void add(Player player){
		UUID uuid = player.getUniqueId();
		int previous = getMap().get(uuid);
		getMap().put(uuid, previous + 1);
	}
	
	public static void syncAll(){
		int delay = 0;
		for (final Tracker tracker : Tracker.values()){
			delay = delay + 20 + Bukkit.getOnlinePlayers().size() * 50;
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable(){
				public void run(){
					int playerDelay = 0;
					for (final Player player : Bukkit.getOnlinePlayers()){
						playerDelay = playerDelay + 50;
						Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable(){
							public void run(){
								syncWithDatabase(player, tracker);
							}
						}, playerDelay);
					}
				}
			}, delay);
		}
	}
	
	public static synchronized void syncWithDatabase(final Player player, final Tracker tracker){
		Logger.log(LogLevel.INFO, "Tracker", "Syncronising " + tracker + " for " + player.getName());
		Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), new Runnable(){
			public void run() {
				UUID uuid = player.getUniqueId();
				String tbl = tracker.getTable();
				int value = tracker.getMap().get(uuid);
				try {
		        	MySQL.openConnection();
		        	int stat = 0;
		        	boolean containsPlayer = false;
		        	
		    		PreparedStatement check = MySQL.prepareStatement("SELECT * FROM `" + tbl + "` WHERE uuid=?;");
		    		check.setString(1, uuid.toString());
		    		ResultSet resultSet = check.executeQuery();
		    		containsPlayer = resultSet.next();
		    			
		    		check.close();
		    		resultSet.close();
		        	
		        	if (containsPlayer){
		        		PreparedStatement sql = MySQL.prepareStatement("SELECT count FROM `" + tbl + "` WHERE uuid=?;");
		        		sql.setString(1, uuid.toString());
		        		
		        		ResultSet result = sql.executeQuery();
		        		result.next();
		        		
		        		stat = result.getInt("count");
		        		
		        		result.close();
		        		sql.close();
		        		
		        		PreparedStatement newlogins = MySQL.prepareStatement("UPDATE `" + tbl + "` SET count=? WHERE uuid=?;");
		        		newlogins.setInt(1, stat + value);
		        		newlogins.setString(2, uuid.toString());
		        		newlogins.executeUpdate();
		        		newlogins.close();
		        		
		        		PreparedStatement name = MySQL.prepareStatement("UPDATE `" + tbl + "` SET name=? where uuid=?;");
		        		name.setString(1, player.getName());
		        		name.setString(2, uuid.toString());
		        		name.executeUpdate();
		        		
		        		name.close();
		        	} else {
		        		PreparedStatement newplayer = MySQL.prepareStatement("INSERT INTO `" + tbl + "` values(?, ?, ?);");
		        		newplayer.setString(1, uuid.toString());
		        		newplayer.setInt(2, value);
		        		newplayer.setString(3, player.getName());
		        		newplayer.execute();
		        		newplayer.close();
		        	}
		        	
		        	tracker.getMap().put(uuid, 0);
		        	
		        } catch (Exception e){
		        	e.printStackTrace();
		        } finally {
		        	try {
						MySQL.closeConnection();
					} catch (SQLException e) {
						e.printStackTrace();
					}
		        }
			}
		});
	}
	
	public static class PlayerInfo {
		
		public static void syncExp(UPlayer player){
			try {
				MySQL.openConnection();
				Logger.log(LogLevel.INFO, "PlayerInfo", "Updating XP in database for player " + player.getName());
	    		
	    		PreparedStatement sql2 = MySQL.prepareStatement("SELECT * FROM `exp` WHERE uuid=?;");
				sql2.setString(1, player.getUniqueId().toString());
				ResultSet resultSet = sql2.executeQuery();
				boolean containsPlayer = resultSet.next();
				
				sql2.close();
				resultSet.close();
	        	
	        	if (containsPlayer){
	        		int xp = player.getXP();
	        		PreparedStatement updatexp = MySQL.prepareStatement("UPDATE `exp` SET count=? WHERE uuid=?;");
	        		updatexp.setInt(1, xp + 1);
	        		updatexp.setString(2, player.getUniqueId().toString());
	        		updatexp.executeUpdate();
	        		
	        		// XXX Cleanup second PreparedStatement
	        		PreparedStatement name = MySQL.prepareStatement("UPDATE `exp` SET name=? where uuid=?;");
	        		name.setString(1, player.getName());
	        		name.setString(2, player.getUniqueId().toString());
	        		name.executeUpdate();
	        		
	        		name.close();
	        		updatexp.close();
	        	} else {
	        		PreparedStatement newplayer = MySQL.prepareStatement("INSERT INTO `exp` values(?, 0, ?);");
	        		newplayer.setString(1, player.getUniqueId().toString());
	        		newplayer.setString(2, player.getName());
	        		newplayer.execute();
	        		newplayer.close();
	        	}
			} catch (Exception e){
				e.printStackTrace();
			} finally {
				try {
					MySQL.closeConnection();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		public static void syncGuild(UPlayer player){
			try {
				MySQL.openConnection();
				Logger.log(LogLevel.INFO, "PlayerInfo", "Updating guild in database for player " + player.getName());
	    		
	    		PreparedStatement sql2 = MySQL.prepareStatement("SELECT * FROM `playerguild` WHERE uuid=?;");
				sql2.setString(1, player.getUniqueId().toString());
				ResultSet resultSet = sql2.executeQuery();
				boolean containsPlayer = resultSet.next();
				
				sql2.close();
				resultSet.close();
	        	
				String guildName;
				if (player.isInGuild()){
					guildName = player.getGuild().getName();
				} else {
					guildName = "Not in a guild";
				}
				
	        	if (containsPlayer){
	        		PreparedStatement update = MySQL.prepareStatement("UPDATE `playerguild` SET guild=?,name=? WHERE uuid=?;");
	        		
	        		update.setString(1, guildName);
	        		update.setString(2, player.getName());
	        		update.setString(3, player.getUniqueId().toString());
	        		
	        		update.executeUpdate();
	        		update.close();
	        	} else {
	        		PreparedStatement newplayer = MySQL.prepareStatement("INSERT INTO `playerguild` values(?, ?, ?);");
	        		newplayer.setString(1, player.getUniqueId().toString());
	        		newplayer.setString(2, guildName);
	        		newplayer.setString(3, player.getName());
	        		newplayer.execute();
	        		newplayer.close();
	        	}
			} catch (Exception e){
				e.printStackTrace();
			} finally {
				try {
					MySQL.closeConnection();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		public static void syncRank(UPlayer player){
			try {
				MySQL.openConnection();
				Logger.log(LogLevel.INFO, "PlayerInfo", "Updating rank in database for player " + player.getName());
	    		
	    		PreparedStatement sql2 = MySQL.prepareStatement("SELECT * FROM `rank` WHERE uuid=?;");
				sql2.setString(1, player.getUniqueId().toString());
				ResultSet resultSet = sql2.executeQuery();
				boolean containsPlayer = resultSet.next();
				
				sql2.close();
				resultSet.close();
	        	
				String rank = player.getGroup().getName();
				
	        	if (containsPlayer){
	        		PreparedStatement update = MySQL.prepareStatement("UPDATE `rank` SET rank=?,name=? WHERE uuid=?;");
	        		
	        		update.setString(1, rank);
	        		update.setString(2, player.getName());
	        		update.setString(3, player.getUniqueId().toString());
	        		
	        		update.executeUpdate();
	        		update.close();
	        	} else {
	        		PreparedStatement newplayer = MySQL.prepareStatement("INSERT INTO `rank` values(?, ?, ?);");
	        		newplayer.setString(1, player.getUniqueId().toString());
	        		newplayer.setString(2, rank);
	        		newplayer.setString(3, player.getName());
	        		newplayer.execute();
	        		newplayer.close();
	        	}
			} catch (Exception e){
				e.printStackTrace();
			} finally {
				try {
					MySQL.closeConnection();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		public static void syncLastSeen(UPlayer player){
			try {
				MySQL.openConnection();
				Logger.log(LogLevel.INFO, "PlayerInfo", "Updating last seen date in database for player " + player.getName());
	    		
	    		PreparedStatement sql2 = MySQL.prepareStatement("SELECT * FROM `last_seen` WHERE uuid=?;");
				sql2.setString(1, player.getUniqueId().toString());
				ResultSet resultSet = sql2.executeQuery();
				boolean containsPlayer = resultSet.next();
				
				sql2.close();
				resultSet.close();
	        	
				String date = player.getLastSeenDate();
				
	        	if (containsPlayer){
	        		PreparedStatement update = MySQL.prepareStatement("UPDATE `last_seen` SET date=?,name=? WHERE uuid=?;");
	        		
	        		update.setString(1, date);
	        		update.setString(2, player.getName());
	        		update.setString(3, player.getUniqueId().toString());
	        		
	        		update.executeUpdate();
	        		update.close();
	        	} else {
	        		PreparedStatement insert = MySQL.prepareStatement("INSERT INTO `last_seen` values(?, ?, ?);");
	        		insert.setString(1, player.getUniqueId().toString());
	        		insert.setString(2, date);
	        		insert.setString(3, player.getName());
	        		insert.execute();
	        		insert.close();
	        	}
			} catch (Exception e){
				e.printStackTrace();
			} finally {
				try {
					MySQL.closeConnection();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
			
	}

}
