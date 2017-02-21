package com.robinmc.ublisk.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.Ublisk;

public class PlayerInfo2 {
	
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
	
	private static final String TABLE_NAME = "player_info_2";
	
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
	}
	
	public static void syncWithDatabase(UPlayer player){
		try {
			UUID uuid = player.getUniqueId();
			String name = player.getName();
			int xp = player.getXP();
			String guildName;
			if (player.isInGuild()){
				guildName = player.getGuild().getName();
			} else {
				guildName = "None";
			}
			String rank = player.getGroup().getName();
			String lastSeenDate = player.getLastSeenDate();
			int level = player.getLevel();
			String lastTown = player.getTown().getName();
			
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
			
			executeQuery(uuid, name, xp, guildName, rank, lastSeenDate, level, lastTown, rightClicked, leftClicked, joinCount, mobKills, lootFound, chatMessages, votingBoxesOpened, inventoryClicks, entityClicks, commandsExecuted);
			
			resetHashMaps(player);
		} catch (SQLException e){
			e.printStackTrace(); // TODO Some fancy log message?
		}
	}
	
	private static void executeQuery(UUID uuid, String name, 
			int xp, String guildName, String rank, String lastSeenDate, 
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
						+ "guild," //4
						+ "rank," //5
						+ "last_seen," //6
						+ "level," //7
						+ "last_town," //8
						+ "right_clicked," //9
						+ "left_clicked," //10
						+ "join_count," //11
						+ "mob_kills," //12
						+ "loot_found," //13
						+ "chat_messages," //14
						+ "vote_box," //15
						+ "inv_click," //16
						+ "entity_click," //17
						+ "commands_executed) " //18
						+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
						
						+ "ON DUPLICATE KEY UPDATE "
						+ "name=?," //19
						+ "xp=?," //20
						+ "guild=?," //21
						+ "rank=?," //22
						+ "last_seen=?," //23
						+ "level=?," //24
						+ "last_town=?," //25
						+ "right_clicked=right_clicked+?," //26
						+ "left_clicked=left_clicked+?," //27
						+ "join_count=join_count+?," //28
						+ "mob_kills=mob_kills+?," //29
						+ "loot_found=loot_found+?," //30
						+ "chat_messages=chat_messages+?," //31
						+ "vote_box=vote_box+?," //32
						+ "inv_click=inv_click+?," //33
						+ "entity_click=entity_click+?," //34
						+ "commands_executed=commands_executed+?;"); //35
			
			statement.setString(1, uuid.toString());
			statement.setString(2, name);
			statement.setInt(3, xp);
			statement.setString(4, guildName);
			statement.setString(5, rank);
			statement.setString(6, lastSeenDate);
			statement.setInt(7, level);
			statement.setString(8, lastTown);
			statement.setInt(9, rightClicked);
			statement.setInt(10, leftClicked);
			statement.setInt(11, joinCount);
			statement.setInt(12, mobKills);
			statement.setInt(13, lootFound);
			statement.setInt(14, chatMessages);
			statement.setInt(15, voteBoxes);
			statement.setInt(16, inventoryClicks);
			statement.setInt(17, entityClicks);
			statement.setInt(18, commandsExecuted);
			
			statement.setString(19, name);
			statement.setInt(20, xp);
			statement.setString(21, guildName);
			statement.setString(22, rank);
			statement.setString(23, lastSeenDate);
			statement.setInt(24, level);
			statement.setString(25, lastTown);
			statement.setInt(26, rightClicked);
			statement.setInt(27, leftClicked);
			statement.setInt(28, joinCount);
			statement.setInt(29, mobKills);
			statement.setInt(30, lootFound);
			statement.setInt(31, chatMessages);
			statement.setInt(32, voteBoxes);
			statement.setInt(33, inventoryClicks);
			statement.setInt(34, entityClicks);
			statement.setInt(35, commandsExecuted);
			
			statement.execute();
			
		} catch (SQLException e){
			throw e;
		} finally {
			if (statement != null) statement.close();
			if (connection != null) connection.close();
		}
		
	}

}