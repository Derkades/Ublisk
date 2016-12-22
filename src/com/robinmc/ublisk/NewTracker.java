package com.robinmc.ublisk;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.robinmc.ublisk.utils.Logger;
import com.robinmc.ublisk.utils.Logger.LogLevel;
import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.Ublisk;

public class NewTracker {
	
	private static final String TABLE = "trackers";
	
	public static final Map<UUID, Integer> RIGHT_CLICKED = new HashMap<>();
	public static final Map<UUID, Integer> LEFT_CLICKED = new HashMap<>();
	public static final Map<UUID, Integer> JOIN_COUNT = new HashMap<>();
	public static final Map<UUID, Integer> MOB_KILLS = new HashMap<>();
	public static final Map<UUID, Integer> LOOT_FOUND = new HashMap<>();
	public static final Map<UUID, Integer> CHAT_MESSAGES = new HashMap<>();
	public static final Map<UUID, Integer> VOTE_BOX = new HashMap<>();
	public static final Map<UUID, Integer> INV_CLICK = new HashMap<>();
	public static final Map<UUID, Integer> ENTITY_CLICK = new HashMap<>();
	
	public static void resetHashMaps(UPlayer player){
		UUID key = player.getUniqueId();
		RIGHT_CLICKED.put(key, 0);
		LEFT_CLICKED.put(key, 0);
		JOIN_COUNT.put(key, 0);
		MOB_KILLS.put(key, 0);
		LOOT_FOUND.put(key, 0);
		CHAT_MESSAGES.put(key, 0);
		VOTE_BOX.put(key, 0);
		INV_CLICK.put(key, 0);
		ENTITY_CLICK.put(key, 0);
	}
	
	public static void syncWithDatabase(UPlayer player){
		try {
			if (containsUUID(player.getUniqueId())){
				Logger.log(LogLevel.INFO, "Updating trackers for " + player.getName());
				updateData(player);
			} else {
				Logger.log(LogLevel.INFO, "Inserting trackers for " + player.getName());
				insertData(player);
			}
		} catch (SQLException e){
			Ublisk.dealWithException(e, "Failed to sync trackers for " + player.getName());
		}
	}
	
	private static void insertData(UPlayer player) throws SQLException {
		Connection connection = null;
		PreparedStatement sql = null;
		try {
			connection = Ublisk.getNewDatabaseConnection("Tracker (" + player.getName() + ")");
			
    		sql = connection.prepareStatement("INSERT INTO `" + TABLE + "` values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
    		
    		sql.setString(1, player.getUniqueId().toString());
    		sql.setInt(2, 0);
    		sql.setInt(3, 0);
    		sql.setInt(4, 0);
    		sql.setInt(5, 0);
    		sql.setInt(6, 0);
    		sql.setInt(7, 0);
    		sql.setInt(8, 0);
    		sql.setInt(9, 0);
    		sql.setInt(10, 0);
    		
    		sql.execute();
		} catch (SQLException e){
			throw e;
		} finally {
			sql.close();
			connection.close();
		}
	}
	
	private static void updateData(UPlayer player) throws SQLException {
		Connection connection = null;

		try {
			UUID key = player.getUniqueId();
			
			connection = Ublisk.getNewDatabaseConnection("Tracker (" + player.getName() + ")");
			
			PreparedStatement getCurrent = connection.prepareStatement("SELECT * FROM `" + TABLE + "` WHERE uuid=?;");
			getCurrent.setString(1, key.toString());
			ResultSet current = getCurrent.executeQuery();
			
			current.next();
			
			int rightClick = current.getInt("right_click");
			int leftClick = current.getInt("left_click");
			int joinCount = current.getInt("join_count");
			int mobKills = current.getInt("mob_kills");
			int lootFound = current.getInt("loot_found");
			int chatMessages = current.getInt("chat_msg");
			int voteBox = current.getInt("vote_box");
			int invClick = current.getInt("inv_click");
			int entityClick = current.getInt("entity_click");
			
			getCurrent.close();
			current.close();
			
    		PreparedStatement update = connection.prepareStatement("UPDATE `" + TABLE + "` SET "
    				+ "right_click=?,"
    				+ "left_click=?,"
    				+ "join_count=?,"
    				+ "mob_kills=?,"
    				+ "loot_found=?,"
    				+ "chat_msg=?,"
    				+ "vote_box=?,"
    				+ "inv_click=?,"
    				+ "entity_click=?"
    				+ " WHERE uuid=?;");
    		
    		update.setInt(1, rightClick + RIGHT_CLICKED.get(key));
    		update.setInt(2, leftClick + LEFT_CLICKED.get(key));
    		update.setInt(3, joinCount + JOIN_COUNT.get(key));
    		update.setInt(4, mobKills + MOB_KILLS.get(key));
    		update.setInt(5, lootFound + LOOT_FOUND.get(key));
    		update.setInt(6, chatMessages + CHAT_MESSAGES.get(key));
    		update.setInt(7, voteBox + VOTE_BOX.get(key));
    		update.setInt(8, invClick + INV_CLICK.get(key));
    		update.setInt(9, entityClick + ENTITY_CLICK.get(key));
    		update.setString(9, key.toString());
    		
    		update.executeUpdate();
    		update.close();
    		
    		resetHashMaps(player);
		} catch (SQLException e){
			throw e;
		} finally {
			connection.close();
		}	
	}
	
	private static boolean containsUUID(UUID uuid) throws SQLException {
		boolean containsPlayer = false;
		
		Connection connection = null;
		PreparedStatement check = null;
		ResultSet result = null;
		try {
			connection = Ublisk.getNewDatabaseConnection("Tracker (contains)");
			check = connection.prepareStatement("SELECT * FROM `" + TABLE + "` WHERE uuid=?;");
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