package com.robinmc.ublisk.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.robinmc.ublisk.utils.Logger;
import com.robinmc.ublisk.utils.Logger.LogLevel;
import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.Ublisk;

public class PlayerInfo {
	
	/*
	 * Table layout:
	 * name - type - default value
	 * uuid - varchar(40) - none
	 * name - varchar(30) - none
	 * xp - int - 0
	 * guild - varchar(100) - "None"
	 * rank - varchar(30) - "Member"
	 * last_seen - varchar(100) - none
	 * level - int - 0
	 * last_town - varchar(100) - none
	 * right_clicked - int - 0
	 * left_clicked - int - 0
	 * join_count - int - 0
	 * mob_kills - int - 0
	 * loot_found - int - 0
	 * chat_messages - int - 0
	 * vote_box - int - 0
	 * inv_click - int - 0
	 * entity_click - int - 0
	 * commands_executed - int - 0
	 */
	
	public static final String TABLE_NAME = "player_info_2";
	
	public static final Map<UUID, Integer> RIGHT_CLICKED = new HashMap<>();
	public static final Map<UUID, Integer> LEFT_CLICKED = new HashMap<>();
	public static final Map<UUID, Integer> JOIN_COUNT = new HashMap<>();
	public static final Map<UUID, Integer> MOB_KILLS = new HashMap<>();
	public static final Map<UUID, Integer> LOOT_FOUND = new HashMap<>();
	public static final Map<UUID, Integer> CHAT_MESSAGES = new HashMap<>();
	public static final Map<UUID, Integer> VOTE_BOX = new HashMap<>();
	public static final Map<UUID, Integer> INV_CLICK = new HashMap<>();
	public static final Map<UUID, Integer> ENTITY_CLICK = new HashMap<>();
	public static final Map<UUID, Integer> COMMANDS_EXECUTED = new HashMap<>();
	public static final Map<UUID, Integer> ABILITIES = new HashMap<>();
	public static final Map<UUID, Integer> BLOCKS_WALKED = new HashMap<>();
	
	public static void resetHashMaps(UPlayer player){
		UUID uuid = player.getUniqueId();
		RIGHT_CLICKED.put(uuid, 0);
		LEFT_CLICKED.put(uuid, 0);
		JOIN_COUNT.put(uuid, 0);
		MOB_KILLS.put(uuid, 0);
		LOOT_FOUND.put(uuid, 0);
		CHAT_MESSAGES.put(uuid, 0);
		VOTE_BOX.put(uuid, 0);
		INV_CLICK.put(uuid, 0);
		ENTITY_CLICK.put(uuid, 0);
		COMMANDS_EXECUTED.put(uuid, 0);
		ABILITIES.put(uuid, 0); // TODO Sync with database
		BLOCKS_WALKED.put(uuid, 0); // TOOD Sync with database
	}
	
	public static void syncWithDatabase(UPlayer player){
		try {
			UUID uuid = player.getUniqueId();
			String name = player.getName();
			int xp = player.getXP();
			String rank = player.getGroup().getName();
			String lastSeenDate = player.getLastSeenDate();
			int level = player.getLevel();
			String lastTown = player.getLastTown().getName();
			
			int rightClicked = RIGHT_CLICKED.get(uuid);
			int leftClicked = LEFT_CLICKED.get(uuid);
			int joinCount = JOIN_COUNT.get(uuid);
			int mobKills = MOB_KILLS.get(uuid);
			int lootFound = LOOT_FOUND.get(uuid);
			int chatMessages = CHAT_MESSAGES.get(uuid);
			int votingBoxesOpened = VOTE_BOX.get(uuid);
			int inventoryClicks = INV_CLICK.get(uuid);
			int entityClicks = ENTITY_CLICK.get(uuid);
			int commandsExecuted = COMMANDS_EXECUTED.get(uuid);
			
			executeQuery(uuid, name, xp, rank, lastSeenDate, level, lastTown, rightClicked, leftClicked, joinCount, mobKills, lootFound, chatMessages, votingBoxesOpened, inventoryClicks, entityClicks, commandsExecuted);
			
			resetHashMaps(player);
		} catch (SQLException e){
			Logger.log(LogLevel.SEVERE, "An error occured while syncing player info for " + player.getName());
			e.printStackTrace();
		}
	}
	
	private static void executeQuery(UUID uuid, String name, 
			int xp, String rank, String lastSeenDate, 
			int level, String lastTown, int rightClicked, int leftClicked, 
			int joinCount, int mobKills, int lootFound, int chatMessages, 
			int voteBoxes, int inventoryClicks, int entityClicks,int commandsExecuted) 
					throws SQLException {
		Connection connection = null;
		PreparedStatement statement = null;
		
		try {
			connection = Ublisk.getNewDatabaseConnection("Player info 2 sync");
			
			statement = connection.prepareStatement(
						"INSERT INTO " + TABLE_NAME + " "
						+ "(uuid, " //1
						+ "name," //2
						+ "xp," //3
						+ "guild," //None
						+ "rank," //4
						+ "last_seen," //5
						+ "level," //6
						+ "last_town," //7
						+ "right_clicked," //8
						+ "left_clicked," //9
						+ "join_count," //10
						+ "mob_kills," //11
						+ "loot_found," //12
						+ "chat_messages," //13
						+ "vote_box," //14
						+ "inv_click," //15
						+ "entity_click," //16
						+ "commands_executed) " //17
						+ "VALUES (?, ?, ?, 'None', ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
						
						+ "ON DUPLICATE KEY UPDATE "
						+ "name=?," //18
						+ "xp=?," //19
						+ "rank=?," //20
						+ "last_seen=?," //21
						+ "level=?," //22
						+ "last_town=?," //23
						+ "right_clicked=right_clicked+?," //24
						+ "left_clicked=left_clicked+?," //25
						+ "join_count=join_count+?," //26
						+ "mob_kills=mob_kills+?," //27
						+ "loot_found=loot_found+?," //28
						+ "chat_messages=chat_messages+?," //29
						+ "vote_box=vote_box+?," //30
						+ "inv_click=inv_click+?," //31
						+ "entity_click=entity_click+?," //32
						+ "commands_executed=commands_executed+?;"); //33
			
			statement.setString(1, uuid.toString());
			statement.setString(2, name);
			statement.setInt(3, xp);
			statement.setString(4, rank);
			statement.setString(5, lastSeenDate);
			statement.setInt(6, level);
			statement.setString(7, lastTown);
			statement.setInt(8, rightClicked);
			statement.setInt(9, leftClicked);
			statement.setInt(10, joinCount);
			statement.setInt(11, mobKills);
			statement.setInt(12, lootFound);
			statement.setInt(13, chatMessages);
			statement.setInt(14, voteBoxes);
			statement.setInt(15, inventoryClicks);
			statement.setInt(16, entityClicks);
			statement.setInt(17, commandsExecuted);
			
			statement.setString(18, name);
			statement.setInt(19, xp);
			statement.setString(20, rank);
			statement.setString(21, lastSeenDate);
			statement.setInt(22, level);
			statement.setString(23, lastTown);
			statement.setInt(24, rightClicked);
			statement.setInt(25, leftClicked);
			statement.setInt(26, joinCount);
			statement.setInt(27, mobKills);
			statement.setInt(28, lootFound);
			statement.setInt(29, chatMessages);
			statement.setInt(30, voteBoxes);
			statement.setInt(31, inventoryClicks);
			statement.setInt(32, entityClicks);
			statement.setInt(33, commandsExecuted);
			
			statement.execute();
			
		} catch (SQLException e){
			throw e;
		} finally {
			if (statement != null) statement.close();
			if (connection != null) connection.close();
		}
		
	}

}