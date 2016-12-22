package com.robinmc.ublisk.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import com.robinmc.ublisk.utils.Logger.LogLevel;
import com.robinmc.ublisk.utils.exception.PlayerNotFoundException;

public class UUIDUtils {
	
	public static UUID getIdFromName(String name){
		String string = DataFile.UUID.getString("uuid." + name);
		return UUID.fromString(string);
	}
	
	public static String getNameFromId(UUID uuid){
		String name = DataFile.UUID.getString("name." + uuid);
		return name;
	}
	
	public static UUID fromString(String string){
		UUID uuid = UUID.fromString(string);
		return uuid;
	}
	
	public static String getNameFromIdString(String string){
		String name = getNameFromId(fromString(string));
		return name;
	}
	
	public static OfflinePlayer getPlayerFromId(UUID uuid){
		return Bukkit.getOfflinePlayer(uuid);
	}
	
	public static OfflinePlayer getOfflinePlayerFromName(String name) throws PlayerNotFoundException{
		try {
			return Bukkit.getOfflinePlayer(getIdFromName(name));
		} catch (Exception e){
			throw new PlayerNotFoundException();
		}
	}
	
	public static Player getPlayerFromName(String name) throws PlayerNotFoundException {
		try {
			return Bukkit.getPlayer(name);
		} catch (Exception e){
			throw new PlayerNotFoundException();
		}
	}
	
	public static void save(UPlayer player){
		DataFile.UUID.set("uuid." + player.getName(), player.getUniqueId().toString());
		DataFile.UUID.set("name." + player.getUniqueId(), player.getName());
		
		syncWithDatabase(player);
	}
	
	private static void syncWithDatabase(UPlayer player){
		try {
			if (containsPlayer(player)){
				updateInDatabase(player);
			} else {
				insertIntoDatabase(player);
			}
		} catch (SQLException e){
			Logger.log(LogLevel.SEVERE, "An error occured while syncing player UUID/NAME with database.");
			e.printStackTrace();
		}
	}
	
	private static boolean containsPlayer(UPlayer player) throws SQLException {
		boolean containsPlayer = false;
		
		Connection connection = null;
		PreparedStatement check = null;
		ResultSet result = null;
		try {
			connection = Ublisk.getNewDatabaseConnection("UUID/NAME (contains)");
			check = connection.prepareStatement("SELECT * FROM `uuid` WHERE name=?;");
			check.setString(1, player.getName());
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
	
	private static void updateInDatabase(UPlayer player) throws SQLException {
		Connection connection = null;
		PreparedStatement update = null;
		
		try {
			connection = Ublisk.getNewDatabaseConnection("UUID/NAME update");
			
			update = connection.prepareStatement("UPDATE `uuid` SET uuid=? WHERE name=?;");
			
			update.setString(2, player.getName());
			update.setString(1, player.getUniqueId().toString());
			
			update.executeUpdate();			
		} catch (SQLException e){
			throw e;
		} finally {
			update.close();
			connection.close();
		}
	}
	
	private static void insertIntoDatabase(UPlayer player) throws SQLException {
		Connection connection = null;
		PreparedStatement insert = null;
		
		try {
			connection = Ublisk.getNewDatabaseConnection("UUID/NAME insert");
			
			insert = connection.prepareStatement("INSERT INTO `uuid` values(?,?);");
			
			insert.setString(2, player.getUniqueId().toString());
			insert.setString(1, player.getName());
			
			insert.execute();
		} catch (SQLException e){
			throw e;
		} finally {
			insert.close();
			connection.close();
		}
		
	}

}
