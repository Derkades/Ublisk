package com.robinmc.ublisk.database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import org.bukkit.Bukkit;
import org.kohsuke.github.GHIssueState;

import com.robinmc.ublisk.modules.GitHubModule;
import com.robinmc.ublisk.modules.TPS;
import com.robinmc.ublisk.utils.Ublisk;

public class ServerInfo {
	
	private static final String TABLE_NAME = "server_info";
	private static final String DUMMY = "ublisk";
	
	public static int AUTORESTART_COUNT = 0;
	public static int DATABASE_REQUESTS = 0;
	public static int CHUNKS_LOADED = 0;
	
	public static void syncWithDatabase() throws SQLException {
		String time = new SimpleDateFormat("HH:mm").format(System.currentTimeMillis());
		int playersOnline = Bukkit.getOnlinePlayers().size();
		int autoRestart = AUTORESTART_COUNT;
		AUTORESTART_COUNT = 0;
		int databaseRequests = DATABASE_REQUESTS;
		DATABASE_REQUESTS = 0;
		double tps = TPS.getAverageTPS();
		int chunksLoaded = CHUNKS_LOADED;
		CHUNKS_LOADED = 0;
		int openIssues = -1;
		int closedIssues = 0;
		try {
			openIssues += GitHubModule.getUbliskRepository().getIssues(GHIssueState.OPEN).size() + GitHubModule.getUbliskTexturesRepository().getIssues(GHIssueState.OPEN).size();
			closedIssues += GitHubModule.getUbliskRepository().getIssues(GHIssueState.CLOSED).size() + GitHubModule.getUbliskTexturesRepository().getIssues(GHIssueState.CLOSED).size();
		} catch (IOException  | NullPointerException e){
			e.printStackTrace();
		}
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
						+ "chunks_loaded," //7
						+ "open_issues," //8
						+ "closed_issues) " //9
						+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"
						
						+ "ON DUPLICATE KEY UPDATE "
						+ "time=?," //10
						+ "tps=?," //11
						+ "players_online=?," //12
						+ "autorestart_count=autorestart_count+?," //13
						+ "database_requests=database_requests+?," //14
						+ "chunks_loaded=chunks_loaded+?," //15
						+ "open_issues=?," //16
						+ "closed_issues=?;"); //17
			
			statement.setString(1, DUMMY);
			statement.setString(2, time);
			statement.setDouble(3, tps);
			statement.setInt(4, playersOnline);
			statement.setInt(5, autoRestart);
			statement.setInt(6, databaseRequests);
			statement.setInt(7, chunksLoaded);
			statement.setInt(8, openIssues);
			statement.setInt(9, openIssues);
			
			statement.setString(10, time);
			statement.setDouble(11, tps);
			statement.setInt(12, playersOnline);
			statement.setInt(13, autoRestart);
			statement.setInt(14, databaseRequests);
			statement.setInt(15, chunksLoaded);
			statement.setInt(16, openIssues);
			statement.setInt(17, closedIssues);
			
			statement.execute();
			
		} catch (SQLException e){
			throw e;
		} finally {
			if (statement != null) statement.close();
			if (connection != null) connection.close();
		}
	}

}
