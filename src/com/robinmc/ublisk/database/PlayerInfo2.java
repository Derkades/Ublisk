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
	 * xp - int - none
	 * guild - varchar(100) - none
	 * rank - varchar(30) - none
	 * last_seen - varchar(200) - none
	 * level - int - none
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
			
			//Reset hashmaps
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
			statement = connection.prepareStatement(""
					+ "IF EXISTS (SELECT * FROM " + TABLE_NAME + " WHERE uuid=?) "
						+ "UPDATE " + TABLE_NAME + " SET "
						+ "name=?,"
						+ "xp=?,"
						+ "guild=?,"
						+ "rank=?,"
						+ "last_seen=?,"
						+ "level=?,"
						+ "last_town=?,"
						+ "right_clicked=right_clicked+?,"
						+ "left_clicked=left_clicked+?,"
						+ "join_count=join_count+?,"
						+ "mob_kills=mob_kills+?,"
						+ "loot_found=loot_found+?,"
						+ "chat_messages=chat_messages+?,"
						+ "vote_box=vote_box+?,"
						+ "inv_click=inv_click+?,"
						+ "entity_click=entity_click+?,"
						+ "commands_executed=commands_executed+?"
						+ " "
						+ "WHERE uuid=?"
					+ " ELSE "
						+ "INSERT INTO " + TABLE_NAME + " "
						+ "(uuid, name, xp, guild, rank, last_seen, level, last_town) "
						+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)"
					+ ";");
			
			statement.setString(1, uuid.toString());
			
			statement.setString(2, name);
			statement.setInt(3, xp);
			statement.setString(4, guildName);
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
			statement.setString(18, uuid.toString());

			statement.setString(19, uuid.toString());
			statement.setString(20, name);
			statement.setInt(21, xp);
			statement.setString(22, guildName);
			statement.setString(23, rank);
			statement.setString(24, lastSeenDate);
			statement.setInt(25, level);
			statement.setString(26, lastTown);
			
			statement.execute();
			
		} catch (SQLException e){
			throw e;
		} finally {
			if (statement != null) statement.close();
			if (connection != null) connection.close();
		}
		
	}

}