package xyz.derkades.ublisk.database;

import java.io.IOException;
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
	//private static final String DUMMY = "ublisk";
	
	public static int AUTORESTART_COUNT = 0;
	public static int DATABASE_REQUESTS = 0;
	public static int CHUNKS_LOADED = 0;
	
	public static void syncWithDatabase() {
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
		
		try (
				PreparedStatement statement = Ublisk.prepareStatement("Server info sync",
						"UPDATE " + TABLE_NAME + " SET"
						+ "time=?,"
						+ "tps=?,"
						+ "players_online=?,"
						+ "autorestart_count=autorestart_count+?,"
						+ "database_requests=database_requests+?,"
						+ "chunks_loaded=chunks_loaded+?,"
						+ "open_issues=?,"
						+ "closed_issues=?, "
						+ "cached_yes=cached_yes+?, " 
						+ "cached_no=cached_no+?",
					
						time,
						tps,
						playersOnline,
						autoRestart,
						databaseRequests,
						chunksLoaded,
						openIssues,
						closedIssues,
						cachedYes,
						cachedNo);
				){
			
			statement.execute();
		} catch (SQLException e){
			Ublisk.exception(e, ServerInfo.class);
		}
	}

}
