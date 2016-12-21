package com.robinmc.ublisk;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;

import com.robinmc.ublisk.utils.Logger;
import com.robinmc.ublisk.utils.Logger.LogLevel;
import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.Ublisk;

@Deprecated
public enum Tracker {
	
	/*
	 * Database layout:
	 * 'uuid' with type TEXT
	 * 'count' with type INT
	 * 'name' with type TEXT
	 */
	
	RIGHT_CLICKED(HashMaps.TRACKER_RIGHT_CLICKED, "right_click"),
	LEFT_CLICKED(HashMaps.TRACKER_LEFT_CLICKED, "left_click"),
	JOINED(HashMaps.TRACKER_JOIN_COUNT, "login_count"),
	MOB_KILLS(HashMaps.TRACKER_MOB_KILLS, "mob_kills"),
	LOOT_FOUND(HashMaps.TRACKER_LOOT_FOUND, "loot_found"),
	CHAT_MESSAGES(HashMaps.TRACKER_CHAT_MESSAGES, "chat_messages"),
	VOTING_BOXES_OPENED(HashMaps.TRACKER_VOTING_BOXES_OPENED, "voting_boxes");
	
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
	
	public void syncWithDatabase(final UPlayer player) throws SQLException {
		Logger.log(LogLevel.INFO, "Tracker", "Syncronising " + this + " for " + player.getName());
		UUID uuid = player.getUniqueId();
		String table = this.getTable();
		int value = this.getMap().get(uuid);
    	int current = 0;
    	boolean containsPlayer = containsPlayer(table, uuid);

    	if (containsPlayer){
    		Connection connection = null;
    		PreparedStatement sql = null;
    		ResultSet result = null;
    		PreparedStatement updateValue = null;
    		PreparedStatement updateName = null;
    		try {
    			connection = Ublisk.getNewDatabaseConnection("Tracker (" + this + ")");
    			
    			//Get current value
    			sql = connection.prepareStatement("SELECT count FROM `" + table + "` WHERE uuid=?;");
        		sql.setString(1, uuid.toString());
        		result = sql.executeQuery();
        		result.next();	
        		current = result.getInt("count"); 		
        		
        		//Set new value (current + value)
        		updateValue = connection.prepareStatement("UPDATE `" + table + "` SET count=? WHERE uuid=?;");
        		updateValue.setInt(1, current + value);
        		updateValue.setString(2, uuid.toString());
        		updateValue.executeUpdate();
        		
        		//Update name
        		updateName = connection.prepareStatement("UPDATE `" + table + "` SET name=? where uuid=?;");
        		updateName.setString(1, player.getName());
        		updateName.setString(2, uuid.toString());
        		updateName.executeUpdate();
    		} catch (SQLException e){
    			throw e;
    		} finally {
    			sql.close();
    			result.close();
    			updateValue.close();
    			updateName.close();
    			
    			connection.close();
    		}
    		
    	} else {
    		Connection connection = null;
    		PreparedStatement insertPlayer = null;
    		try {
    			connection = Ublisk.getNewDatabaseConnection("Tracker (" + this + ")");
	    		insertPlayer = connection.prepareStatement("INSERT INTO `" + table + "` values(?, ?, ?);");
	    		insertPlayer.setString(1, uuid.toString());
	    		insertPlayer.setInt(2, value);
	    		insertPlayer.setString(3, player.getName());
	    		insertPlayer.execute();
    		} catch (SQLException e){
    			throw e;
    		} finally {
    			insertPlayer.close();
    			
    			connection.close();
    		}
    	}
    	
    	this.getMap().put(uuid, 0);
	}
	
	private static boolean containsPlayer(String table, UUID uuid) throws SQLException {
		boolean containsPlayer = false;
		
		Connection connection = null;
		PreparedStatement check = null;
		ResultSet result = null;
		try {
			connection = Ublisk.getNewDatabaseConnection("Tracker (containsPlayer)");
			check = connection.prepareStatement("SELECT * FROM `" + table + "` WHERE uuid=?;");
			check.setString(1, uuid.toString());
			result = check.executeQuery();
			containsPlayer = result.next();
		} catch (SQLException e){
			throw e;
		} finally {
			check.close();
			result.close();
			
			connection.close();
		}
		
		return containsPlayer;
	}
}