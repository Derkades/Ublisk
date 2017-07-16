package xyz.derkades.ublisk.database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import org.bukkit.Bukkit;
import org.kohsuke.github.GHIssueState;

import xyz.derkades.ublisk.modules.GitHubModule;
import xyz.derkades.ublisk.modules.TPS;
import xyz.derkades.ublisk.utils.Ublisk;
import xyz.derkades.ublisk.utils.caching.Cache;

public class ServerInfo {
	
	private static final String TABLE_NAME = "server_info";
	private static final String DUMMY = "ublisk";
	
	public static int AUTORESTART_COUNT = 0;
	public static int DATABASE_REQUESTS = 0;
	public static int CHUNKS_LOADED = 0;
	
	public static void syncWithDatabase() throws SQLException {
		final String time = new SimpleDateFormat("HH:mm").format(System.currentTimeMillis());
		final int playersOnline = Bukkit.getOnlinePlayers().size();
		final int autoRestart = AUTORESTART_COUNT;
		AUTORESTART_COUNT = 0;
		final int databaseRequests = DATABASE_REQUESTS;
		DATABASE_REQUESTS = 0;
		final double tps = TPS.getAverageTPS();
		final int chunksLoaded = CHUNKS_LOADED;
		CHUNKS_LOADED = 0;
		int openIssues = 0;
		int closedIssues = 0;
		try {
			openIssues = GitHubModule.getUbliskRepository().getIssues(GHIssueState.OPEN).size() + GitHubModule.getUbliskTexturesRepository().getIssues(GHIssueState.OPEN).size();
			closedIssues = GitHubModule.getUbliskRepository().getIssues(GHIssueState.CLOSED).size() + GitHubModule.getUbliskTexturesRepository().getIssues(GHIssueState.CLOSED).size();
		} catch (IOException  | NullPointerException e){
			e.printStackTrace();
		}
		final int cachedYes = Cache.CACHED_OBJECTS_STATISTIC[0];
		Cache.CACHED_OBJECTS_STATISTIC[0] = 0;
		final int cachedNo = Cache.CACHED_OBJECTS_STATISTIC[1];
		Cache.CACHED_OBJECTS_STATISTIC[1] = 0;
		
		Connection connection = null;
		PreparedStatement statement = null;
		
		try {
			connection = Ublisk.getDatabaseConnection("Server info sync");
			
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
						+ "closed_issues," //9
						+ "cached_yes," //10
						+ "cached_no) " //11
						+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
						
						+ "ON DUPLICATE KEY UPDATE "
						+ "time=?," //12
						+ "tps=?," //13
						+ "players_online=?," //14
						+ "autorestart_count=autorestart_count+?," //15
						+ "database_requests=database_requests+?," //16
						+ "chunks_loaded=chunks_loaded+?," //17
						+ "open_issues=?," //18
						+ "closed_issues=?, " //19
						+ "cached_yes=?, " //20
						+ "cached_no=?"); //21
			
			statement.setString(1, DUMMY);
			statement.setString(2, time);
			statement.setDouble(3, tps);
			statement.setInt(4, playersOnline);
			statement.setInt(5, autoRestart);
			statement.setInt(6, databaseRequests);
			statement.setInt(7, chunksLoaded);
			statement.setInt(8, openIssues);
			statement.setInt(9, openIssues);
			statement.setInt(10, cachedYes);
			statement.setInt(11, cachedNo);
			
			statement.setString(12, time);
			statement.setDouble(13, tps);
			statement.setInt(14, playersOnline);
			statement.setInt(15, autoRestart);
			statement.setInt(16, databaseRequests);
			statement.setInt(17, chunksLoaded);
			statement.setInt(18, openIssues);
			statement.setInt(19, closedIssues);
			statement.setInt(20, cachedYes);
			statement.setInt(21, cachedNo);
			
			statement.execute();
			
		} catch (SQLException e){
			throw e;
		} finally {
			if (statement != null) statement.close();
		}
	}

}
