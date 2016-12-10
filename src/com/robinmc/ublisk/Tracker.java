package com.robinmc.ublisk;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;

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
	
	@Deprecated
	public static void syncAll(){
		/*
		int delay = 0;
		for (final Tracker tracker : Tracker.values()){
			delay = delay + Var.TRACKER_DELAY + Bukkit.getOnlinePlayers().size() * 50;
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable(){
				public void run(){
					int playerDelay = 0;
					for (final Player player : Bukkit.getOnlinePlayers()){
						playerDelay = playerDelay + 50;
						Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable(){
							public void run(){
								//syncWithDatabase(player, tracker);
							}
						}, playerDelay);
					}
				}
			}, delay);
		}
		*/
	}
	
	public void syncWithDatabase(final UPlayer player){
		Logger.log(LogLevel.INFO, "Tracker", "Syncronising " + this + " for " + player.getName());
				UUID uuid = player.getUniqueId();
				String table = this.getTable();
				int value = this.getMap().get(uuid);
				try {
		        	MySQL.openConnection();
		        	int stat = 0;
		        	boolean containsPlayer = false;
		        	
		    		PreparedStatement check = MySQL.prepareStatement("SELECT * FROM `" + table + "` WHERE uuid=?;");
		    		check.setString(1, uuid.toString());
		    		ResultSet resultSet = check.executeQuery();
		    		containsPlayer = resultSet.next();
		    			
		    		check.close();
		    		resultSet.close();
		        	
		        	if (containsPlayer){
		        		PreparedStatement sql = MySQL.prepareStatement("SELECT count FROM `" + table + "` WHERE uuid=?;");
		        		sql.setString(1, uuid.toString());
		        		
		        		ResultSet result = sql.executeQuery();
		        		result.next();
		        		
		        		stat = result.getInt("count");
		        		
		        		result.close();
		        		sql.close();
		        		
		        		PreparedStatement newlogins = MySQL.prepareStatement("UPDATE `" + table + "` SET count=? WHERE uuid=?;");
		        		newlogins.setInt(1, stat + value);
		        		newlogins.setString(2, uuid.toString());
		        		newlogins.executeUpdate();
		        		newlogins.close();
		        		
		        		PreparedStatement name = MySQL.prepareStatement("UPDATE `" + table + "` SET name=? where uuid=?;");
		        		name.setString(1, player.getName());
		        		name.setString(2, uuid.toString());
		        		name.executeUpdate();
		        		
		        		name.close();
		        	} else {
		        		PreparedStatement newplayer = MySQL.prepareStatement("INSERT INTO `" + table + "` values(?, ?, ?);");
		        		newplayer.setString(1, uuid.toString());
		        		newplayer.setInt(2, value);
		        		newplayer.setString(3, player.getName());
		        		newplayer.execute();
		        		newplayer.close();
		        	}
		        	
		        	this.getMap().put(uuid, 0);
		        	
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