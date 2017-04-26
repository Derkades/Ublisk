package com.robinmc.ublisk.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import org.bukkit.Bukkit;

import com.robinmc.ublisk.utils.Ublisk;

public class ServerInfo {
	
	/*
	 * Table layout:
	 * dummy - varchar(10) primary key
	 * time - varchar(30)
	 * tps - double
	 * players_online - int
	 * autorestart_count - int
	 * database_requests - int
	 */
	
	private static final String TABLE_NAME = "server_info";
	private static final String DUMMY = "ublisk";
	
	public static int AUTORESTART_COUNT = 0;
	public static int DATABASE_REQUESTS = 0;
	
	public static void syncWithDatabase() throws SQLException {
		String time = new SimpleDateFormat("HH:mm").format(System.currentTimeMillis());
		int playersOnline = Bukkit.getOnlinePlayers().size();
		int autoRestart = AUTORESTART_COUNT;
		AUTORESTART_COUNT = 0;
		int databaseRequests = DATABASE_REQUESTS;
		DATABASE_REQUESTS = 0;
		double tps = 10.0; // TODO TPS average
		
		Connection connection = null;
		PreparedStatement statement = null;
		
		try {
			connection = Ublisk.getNewDatabaseConnection("Player info 2 sync");
			
			statement = connection.prepareStatement(
						"INSERT INTO " + TABLE_NAME + " "
						+ "(dummy, " //1
						+ "time," //2
						+ "tps," //3
						+ "players_online," //4
						+ "autorestart_count," //5
						+ "database_requests," //6
						+ "VALUES (?, ?, ?, ?, ?, ?)"
						
						+ "ON DUPLICATE KEY UPDATE "
						+ "time=?," //7
						+ "tps=?" //8
						+ "players_online=?" //9
						+ "autorestart_count=autorestart_count+?" //10
						+ "database_requests=database_requests+?;"); //11
			
			statement.setString(1, DUMMY);
			statement.setString(2, time);
			statement.setDouble(3, tps);
			statement.setInt(4, playersOnline);
			statement.setInt(5, autoRestart);
			statement.setInt(6, databaseRequests);
			
			statement.setString(7, time);
			statement.setDouble(8, tps);
			statement.setInt(9, playersOnline);
			statement.setInt(10, autoRestart);
			statement.setInt(11, databaseRequests);
			
			statement.execute();
			
		} catch (SQLException e){
			throw e;
		} finally {
			if (statement != null) statement.close();
			if (connection != null) connection.close();
		}
	}

}
