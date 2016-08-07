package com.robinmc.ublisk.utils.enums;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.robinmc.ublisk.HashMaps;
import com.robinmc.ublisk.utils.sql.MySQL;

public enum Tracker {
	
	/*
	 * Database layout:
	 * 'uuid' with type TEXT
	 * 'count' with type INT
	 * 'name' with type TEXT
	 */
	
	// FIXME Create missing tables in database
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
	
	// TODO Call this method in a task every minute
	
	public static void syncAll(){
		for (Player player : Bukkit.getOnlinePlayers()){
			for (Tracker tracker : Tracker.values()){
				syncWithDatabase(player, tracker);
			}
		}
	}
	
	// TODO Call this method on logout
	
	public static void syncWithDatabase(Player player, Tracker tracker){
		UUID uuid = player.getUniqueId();
		String tbl = tracker.getTable();
		int value = tracker.getMap().get(uuid);
		try {
        	MySQL.openConnection();
        	int stat = 0;
        	boolean containsPlayer = false;
        	
        	try {
    			PreparedStatement sql = MySQL.prepareStatement("SELECT * FROM `" + tbl + "` WHERE uuid=?;");
    			sql.setString(1, uuid.toString());
    			ResultSet resultSet = sql.executeQuery();
    			containsPlayer = resultSet.next();
    			
    			sql.close();
    			resultSet.close();
    		} catch (SQLException e){
    			e.printStackTrace();
    		} finally {
    			try {
    				MySQL.closeConnection();
    			} catch (SQLException e) {
    				e.printStackTrace();
    			}
    		}
        	
        	if (containsPlayer){
        		PreparedStatement sql = MySQL.prepareStatement("SELECT count FROM `" + tbl + "` WHERE uuid=?;");
        		sql.setString(1, uuid.toString());
        		
        		ResultSet result = sql.executeQuery();
        		result.next();
        		
        		stat = result.getInt("count");
        		
        		PreparedStatement newlogins = MySQL.prepareStatement("UPDATE `" + tbl + "` SET count=? WHERE uuid=?;");
        		newlogins.setInt(1, stat + value);
        		newlogins.setString(2, uuid.toString());
        		newlogins.executeUpdate();
        		
        		PreparedStatement name = MySQL.prepareStatement("UPDATE `" + tbl + "` SET name=? where uuid=?;");
        		name.setString(1, player.getName());
        		name.setString(2, uuid.toString());
        		name.executeUpdate();
        		
        		name.close();
        		newlogins.close();
        		sql.close();
        		result.close();
        	} else {
        		PreparedStatement newplayer = MySQL.prepareStatement("INSERT INTO `" + tbl + "` values(?, ?, ?);");
        		newplayer.setString(1, uuid.toString());
        		newplayer.setInt(2, value);
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
		
		tracker.getMap().put(uuid, 0);
		
	}

}
