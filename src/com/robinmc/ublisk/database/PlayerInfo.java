package com.robinmc.ublisk.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.robinmc.ublisk.utils.Logger;
import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.Ublisk;
import com.robinmc.ublisk.utils.Logger.LogLevel;
import com.robinmc.ublisk.utils.exception.NotInGuildException;

public class PlayerInfo {
	
	private static final String TABLE = "player_info";
	
	public static void syncInfo(UPlayer player){
		try {
			if (containsPlayer(player)){
				Logger.log(LogLevel.INFO, "Updating player info for " + player.getName() + "...");
				updateInfo(player);
			} else {
				Logger.log(LogLevel.INFO, "Inserting player info for " + player.getName() + "...");
				insertInfo(player);
			}
		} catch (SQLException e){
			Logger.log(LogLevel.SEVERE, "An error occured while trying to syncronise player info for " + player.getName());
			e.printStackTrace();
		}
	}
	
	private static boolean containsPlayer(UPlayer player) throws SQLException {
		Connection connection = null;
		PreparedStatement sql = null;
		
		boolean containsPlayer = false;
		
		try {
			connection = Ublisk.getNewDatabaseConnection("Player info containsPlayer");
			sql = connection.prepareStatement("SELECT * FROM `" + TABLE + "` WHERE uuid=?;");
			sql.setString(1, player.getUniqueId().toString());
			ResultSet resultSet = sql.executeQuery();
			containsPlayer = resultSet.next();
		} catch (SQLException e){
			throw e;
		} finally {
			sql.close();
			connection.close();
		}
		
		return containsPlayer;
	}
	
	private static void updateInfo(UPlayer player) throws SQLException {
		Connection connection = null;
		PreparedStatement update = null;
		
		try {
			connection = Ublisk.getNewDatabaseConnection("Update player info");
			
			update = connection.prepareStatement("UPDATE `" + TABLE + "` SET xp=?,guild=?,rank=?,last_seen=?,level=?,last_town=? WHERE uuid=?;");
			
			update.setInt(1, player.getXP());
			try {
				update.setString(2, player.getGuild().getName());
			} catch (NotInGuildException e) {
				update.setString(2, "None");
			}
			update.setString(3, player.getGroup().getName());
			update.setString(4, player.getLastSeenDate());
			update.setInt(5, player.getLevel());
			update.setString(6, player.getTown().getName());
			
			update.setString(7, player.getUniqueId().toString());
			
			update.executeUpdate();
		} catch (SQLException e){
			throw e;
		} finally {
			update.close();
			connection.close();
		}
	}
	
	private static void insertInfo(UPlayer player) throws SQLException {
		Connection connection = null;
		PreparedStatement insert = null;
		
		try {
			connection = Ublisk.getNewDatabaseConnection("Insert player info");
			
			insert = connection.prepareStatement("INSERT INTO `" + TABLE + "` values(?, ?, ?, ?, ?, ?, ?);");
			
			insert.setString(1, player.getUniqueId().toString());
			insert.setInt(2, player.getXP());
			try {
				insert.setString(3, player.getGuild().getName());
			} catch (NotInGuildException e) {
				insert.setString(3, "None");
			}
			insert.setString(4, player.getGroup().getName());
			insert.setString(5, player.getLastSeenDate());
			insert.setInt(6, player.getLevel());
			insert.setString(7, player.getTown().getName());
			
			insert.execute();
		} catch (SQLException e){
			throw e;
		} finally {
			insert.close();
			connection.close();
		}
	}

}
