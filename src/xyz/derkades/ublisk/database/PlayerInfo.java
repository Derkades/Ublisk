package xyz.derkades.ublisk.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.OfflinePlayer;

import xyz.derkades.ublisk.utils.Logger;
import xyz.derkades.ublisk.utils.UPlayer;
import xyz.derkades.ublisk.utils.Ublisk;
import xyz.derkades.ublisk.utils.Logger.LogLevel;

public class PlayerInfo {
	
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
	public static final Map<UUID, Integer> SECONDS_AFK = new HashMap<>();
	public static final Map<UUID, Integer> SECONDS_NOT_AFK = new HashMap<>();
	
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
		ABILITIES.put(uuid, 0);
		BLOCKS_WALKED.put(uuid, 0);
		SECONDS_AFK.put(uuid, 0);
		SECONDS_NOT_AFK.put(uuid, 0);
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
			
			List<String> friendNames = new ArrayList<String>();
			for (OfflinePlayer friend : player.getFriends())
				friendNames.add(friend.getName());
			String friends = String.join(", ", friendNames);
			
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
			int abilities = ABILITIES.get(uuid);
			int blocksWalked = BLOCKS_WALKED.get(uuid);
			int secondsAFK = SECONDS_AFK.get(uuid);
			int secondsNotAFK = SECONDS_NOT_AFK.get(uuid);
			
			executeQuery(uuid, 
					name, 
					xp, 
					rank, 
					lastSeenDate, 
					level, 
					lastTown, 
					friends,
					rightClicked, 
					leftClicked, 
					joinCount, 
					mobKills, 
					lootFound, 
					chatMessages, 
					votingBoxesOpened, 
					inventoryClicks, 
					entityClicks, 
					commandsExecuted,
					abilities,
					blocksWalked,
					secondsAFK,
					secondsNotAFK);
			
			resetHashMaps(player);
		} catch (SQLException e){
			Logger.log(LogLevel.SEVERE, "An error occured while syncing player info for " + player.getName() + ": " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	private static void executeQuery(UUID uuid, String name, 
			int xp, String rank, String lastSeenDate, 
			int level, String lastTown, String friends, int rightClicked, int leftClicked, 
			int joinCount, int mobKills, int lootFound, int chatMessages, 
			int voteBoxes, int inventoryClicks, int entityClicks,int commandsExecuted,
			int abilities, int blocksWalked, int secondsAFK, int secondsNotAFK) 
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
						+ "friends," //8
						+ "right_clicked," //9
						+ "left_clicked," //10
						+ "join_count," //11
						+ "mob_kills," //12
						+ "loot_found," //13
						+ "chat_messages," //14
						+ "vote_box," //15
						+ "inv_click," //16
						+ "entity_click," //17
						+ "commands_executed," //18
						+ "abilities," //19
						+ "blocks_walked," //20
						+ "seconds_afk," //21
						+ "seconds_not_afk) " //22
						+ "VALUES (?, ?, ?, 'None', ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
						
						+ "ON DUPLICATE KEY UPDATE "
						+ "name=?," //23
						+ "xp=?," //24
						+ "rank=?," //25
						+ "last_seen=?," //26
						+ "level=?," //27
						+ "last_town=?," //28
						+ "friends=?," //29
						+ "right_clicked=right_clicked+?," //30
						+ "left_clicked=left_clicked+?," //31
						+ "join_count=join_count+?," //32
						+ "mob_kills=mob_kills+?," //33
						+ "loot_found=loot_found+?," //34
						+ "chat_messages=chat_messages+?," //35
						+ "vote_box=vote_box+?," //36
						+ "inv_click=inv_click+?," //37
						+ "entity_click=entity_click+?," //38
						+ "commands_executed=commands_executed+?," //39
						+ "abilities=abilities+?," //40
						+ "blocks_walked=blocks_walked+?," //41
						+ "seconds_afk=seconds_afk+?," //42
						+ "seconds_not_afk=seconds_not_afk+?;"); //43
			
			statement.setString(1, uuid.toString());
			statement.setString(2, name);
			statement.setInt(3, xp);
			statement.setString(4, rank);
			statement.setString(5, lastSeenDate);
			statement.setInt(6, level);
			statement.setString(7, lastTown);
			statement.setString(8, friends);
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
			statement.setInt(19, abilities);
			statement.setInt(20, blocksWalked);
			statement.setInt(21, secondsAFK);
			statement.setInt(22, secondsNotAFK);
			
			statement.setString(23, name);
			statement.setInt(24, xp);
			statement.setString(25, rank);
			statement.setString(26, lastSeenDate);
			statement.setInt(27, level);
			statement.setString(28, lastTown);
			statement.setString(29, friends);
			statement.setInt(30, rightClicked);
			statement.setInt(31, leftClicked);
			statement.setInt(32, joinCount);
			statement.setInt(33, mobKills);
			statement.setInt(34, lootFound);
			statement.setInt(35, chatMessages);
			statement.setInt(36, voteBoxes);
			statement.setInt(37, inventoryClicks);
			statement.setInt(38, entityClicks);
			statement.setInt(39, commandsExecuted);
			statement.setInt(40, abilities);
			statement.setInt(41, blocksWalked);
			statement.setInt(42, secondsAFK);
			statement.setInt(43, secondsNotAFK);
			
			statement.execute();
			
		} catch (SQLException e){
			throw e;
		} finally {
			if (statement != null) statement.close();
			if (connection != null) connection.close();
		}
		
	}

}