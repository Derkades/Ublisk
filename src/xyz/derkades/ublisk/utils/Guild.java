package xyz.derkades.ublisk.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.scheduler.BukkitRunnable;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import xyz.derkades.derkutils.ListUtils;
import xyz.derkades.ublisk.Main;
import xyz.derkades.ublisk.database.PlayerInfo;
import xyz.derkades.ublisk.utils.Logger.LogLevel;
import xyz.derkades.ublisk.utils.caching.Cache;

public class Guild {

	public static final Map<String, GuildInvite> GUILD_INVITES = new HashMap<>();

	private String name;
	
	/**
	 * Creates a new guild object. This guild may or may not exist.
	 * @param name Guild name
	 */
	public Guild(String name) {
		this.name = name;
	}

	public synchronized boolean exists() {
		Object cache = Cache.getCachedObject("exists:" + this.getName());
		if (cache != null){
			return (boolean) cache;
		}
		
		Connection connection = null;
		PreparedStatement query = null;
		ResultSet resultSet = null;
		boolean contains = true;
		try {
			connection = Ublisk.getNewDatabaseConnection("Guilds check (" + name + ")");
			query = connection.prepareStatement("SELECT * FROM `guilds` WHERE name=?;");
			query.setString(1, name);
			resultSet = query.executeQuery();
			contains = resultSet.next();
		} catch (SQLException e) {
			Logger.log(LogLevel.SEVERE, "Unable to connect to database for getting guild");
			e.printStackTrace();
		} finally {
			try {
				if (query != null) query.close();
				if (resultSet != null) resultSet.close();
				if (connection != null) connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		Cache.addCachedObject("exists:" + this.getName(), contains, 300);
		
		return contains;
	}

	public String getName() {
		return name;
	}

	/**
	 * @param owner The name of the guild owner, for example "Derkades"
	 * @throws IllegalArgumentException If the owner is null or an empty string.
	 * @throws UnsupportedOperationException If a guild with that name already exists
	 */
	public synchronized void create(final UPlayer owner) throws IllegalArgumentException, UnsupportedOperationException {
		if (this.exists())
			throw new UnsupportedOperationException("A guild with this name already exists.");

		if (owner == null || owner.equals(""))
			throw new IllegalArgumentException("Owner cannot be null or an empty string.");
		
		Connection connection = null;
		PreparedStatement insert = null;
		try {
			connection = Ublisk.getNewDatabaseConnection("Create guild (" + this.getName() + ")");
			insert = connection.prepareStatement("INSERT INTO `guilds` (name, owner) values(?, ?);");
			insert.setString(1, this.getName());
			insert.setString(2, owner.getUniqueId().toString());
			insert.execute();
		} catch (SQLException e) {
			Logger.log(LogLevel.SEVERE, "Unable to connect to database for creating guild");
			e.printStackTrace();
		} finally {
			try {
				insert.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		owner.setGuild(this);
		
		Cache.addCachedObject("exists:" + this.getName(), true, 300);
	}

	public synchronized void invitePlayer(final UPlayer source, final UPlayer target) {
		if (!this.exists())
			throw new UnsupportedOperationException("Cannot invite player to non-existent guild.");

		final GuildInvite invite = new GuildInvite(this, source);

		// Add invite to list of invites, and remove it after 60 seconds (if it hasn't been accepted)
		GUILD_INVITES.put(target.getName(), invite);
		new BukkitRunnable() {

			public void run() {
				if (GUILD_INVITES.containsKey(target.getName())) {
					GUILD_INVITES.remove(target.getName());
				}
			}

		}.runTaskLater(Main.getInstance(), 60 * 20);

		TextComponent inviteMessage = new TextComponent(
				source.getName() + " has invited you to join " + this.getName() + ". ");
		inviteMessage.setColor(ChatColor.DARK_AQUA);
		inviteMessage.setBold(true);

		BaseComponent[] hoverText = new ComponentBuilder("Click me!").color(ChatColor.YELLOW).create();

		TextComponent clickToAccept = new TextComponent("Click here to accept.");
		clickToAccept.setColor(ChatColor.AQUA);
		clickToAccept.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hoverText));
		clickToAccept.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/guild accept"));

		BaseComponent[] components = new BaseComponent[] {
				inviteMessage, clickToAccept
		};

		target.sendMessage(components);
	}
	
	public synchronized int getPoints(){
		Object cache =  Cache.getCachedObject("points:" + this.getName());
		
		if (cache != null){
			return (int) cache;
		}
		
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		
		int points = 0;
		try {
			connection = Ublisk.getNewDatabaseConnection("Guild points " + this.getName());
			statement = connection.prepareStatement("SELECT `points` FROM `guilds` WHERE name=?");
			statement.setString(1, this.getName());
			result = statement.executeQuery();
			result.next();
			points = result.getInt("points");
		} catch (SQLException e){
			Logger.log(LogLevel.SEVERE, "Guilds", "Database error while trying to get guild points for " + this.getName());
			e.printStackTrace();
		} finally {
			try {
				if (result != null) result.close();
				if (statement != null) statement.close();
				if (connection != null) connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		Cache.addCachedObject("points:" + this.getName(), points, 300);
		
		return points;
	}
	
	public synchronized void setPoints(int points){		
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = Ublisk.getNewDatabaseConnection("Guild points " + this.getName());
			statement = connection.prepareStatement("UPDATE `guilds` SET points=? WHERE name=?;");
			statement.setInt(1, points);
			statement.setString(2, this.getName());
			statement.executeUpdate();
		} catch (SQLException e){
			Logger.log(LogLevel.SEVERE, "Guilds", "Database error while trying to set guild points for " + this.getName());
			e.printStackTrace();
		} finally {
			try {
				if (statement != null) statement.close();
				if (connection != null) connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		Cache.addCachedObject("points:" + this.getName(), points, 300);
	}
	
	/**
	 * This will also send a message to all guild members.
	 * @param points
	 * @param playerName Name of the player who got the points for the guild
	 */
	public synchronized void addPoints(int points, String playerName){
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = Ublisk.getNewDatabaseConnection("Add points " + this.getName());
			statement = connection.prepareStatement("UPDATE `guilds` SET points=points+? WHERE name=?");
			statement.setInt(1, points);
			statement.setString(2, this.getName());
			statement.executeUpdate();
		} catch (SQLException e){
			Logger.log(LogLevel.SEVERE, "Guilds", "Database error while trying to add guild points for " + this.getName());
			e.printStackTrace();
		} finally {
			try {
				if (statement != null) statement.close();
				if (connection != null) connection.close();
			} catch (SQLException e){
				e.printStackTrace();
			}
		}
		
		Cache.removeCachedObject("points:" + this.getName());
		
		for (UPlayer player : this.getMembers()){
			if (player.isOnline()){
				if (player.getName() != playerName){ //Do not send message to the player who got the points
					player.sendPrefixedMessage("Guilds", playerName + " got " + points + " points for your guild.");
				}
			}
		}
	}
	
	public synchronized List<UPlayer> getMembers(){	
		List<UPlayer> list = new ArrayList<>();
		
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			connection = Ublisk.getNewDatabaseConnection("Get guild members");
			statement = connection.prepareStatement("SELECT `uuid` FROM `player_info_2` WHERE `guild` = ?;");
			statement.setString(1, this.getName());
			result = statement.executeQuery();
			while (result.next()){
				UUID uuid = UUID.fromString(result.getString("uuid"));
				OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
				list.add(new UPlayer(player));
			}
		} catch (SQLException e){
			e.printStackTrace();
		} finally {
			try {
				if (result != null) result.close();
				if (statement != null) statement.close();
				if (connection != null) connection.close();
			} catch (SQLException e){
				e.printStackTrace();
			}
		}

		return list;
	}
	
	public synchronized void remove(){
		Connection connection = null;
		PreparedStatement delete = null;
		try {
			connection = Ublisk.getNewDatabaseConnection("Delete " + this.getName());
			delete = connection.prepareStatement("DELETE FROM `guilds` WHERE name=?;");
			delete.setString(1, this.getName());
			delete.execute();
		} catch (SQLException e){
			Logger.log(LogLevel.SEVERE, "Guilds", "Database error while trying to remove " + this.getName());
			e.printStackTrace();
		} finally {
			try {
				if (delete != null) delete.close();
				if (delete != null) connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		Cache.addCachedObject("exists:" + this.getName(), false, 600);
	}
	
	public synchronized OfflinePlayer getOwner(){
		Object cache = Cache.getCachedObject("owner:" + this.getName());
		if (cache != null){
			return (OfflinePlayer) cache;
		}
		
		Connection connection = null;
		PreparedStatement query = null;
		ResultSet result = null;
		
		String uuid = null;
		try {
			connection = Ublisk.getNewDatabaseConnection("Get guild owner");
			query = connection.prepareStatement("SELECT `owner` FROM `guilds` WHERE name=?");
			query.setString(1, this.getName());
			result = query.executeQuery();
			result.next();
			uuid = result.getString("owner");
		} catch (SQLException e){
			Logger.log(LogLevel.SEVERE, "Guilds", "Database error while trying to get guild points for " + this.getName());
			e.printStackTrace();
		} finally {
			try {
				if (result != null) result.close();
				if (query != null) query.close();
				if (connection != null) connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		OfflinePlayer owner = Bukkit.getOfflinePlayer(UUID.fromString(uuid));
		
		Cache.addCachedObject("owner:" + this.getName(), owner, 1000);
		
		return owner;
	}
	
	public synchronized void setDescription(String description){
		Cache.addCachedObject("description:" + this.getName(), description, 300);
		
		Connection connection = null;
		PreparedStatement statement = null;
		
		try {
			connection = Ublisk.getNewDatabaseConnection("Guilds description update");
			statement = connection.prepareStatement("UPDATE guilds SET description=? WHERE name=?");
			statement.setString(1, description);
			statement.setString(2, name);
			statement.executeUpdate();
		} catch (SQLException e){
			Logger.log(LogLevel.SEVERE, "Guilds", "Database error while trying to set description");
			e.printStackTrace();
		} finally {
			try {
				if (statement != null) statement.close();
				if (connection != null) connection.close();
			} catch (SQLException e){
				e.printStackTrace();
			}
		}
	}
	
	public synchronized String getDescription(){
		Object cache = Cache.getCachedObject("description:" + this.getName());
		if (cache != null){
			return (String) cache;
		}
		
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		
		String description = null;
		try {
			connection = Ublisk.getNewDatabaseConnection("Get description");
			statement = connection.prepareStatement("SELECT description FROM guilds WHERE name=?");
			statement.setString(1, name);
			result = statement.executeQuery();
			result.next();
			description = result.getString("description");
		} catch (SQLException e){
			Logger.log(LogLevel.SEVERE, "Guilds", "A database error occured while trying to get description for " + this.getName());
			e.printStackTrace();
		} finally {
			try {
				if (result != null) result.close();
				if (statement != null) statement.close();
				if (connection != null) connection.close();
			} catch (SQLException e){
				e.printStackTrace();
			}
		}
		
		Cache.addCachedObject("description:" + this.getName(), description, 600);
		
		return description;
	}
	
	public synchronized void setIconURL(String icon){
		Connection connection = null;
		PreparedStatement statement = null;
		
		try {
			connection = Ublisk.getNewDatabaseConnection("Guilds icon update");
			statement = connection.prepareStatement("UPDATE guilds SET icon=? WHERE name=?");
			statement.setString(1, icon);
			statement.setString(2, name);
			statement.executeUpdate();
		} catch (SQLException e){
			Logger.log(LogLevel.SEVERE, "Guilds", "Database error while trying to set icon");
			e.printStackTrace();
		} finally {
			try {
				if (statement != null) statement.close();
				if (connection != null) connection.close();
			} catch (SQLException e){
				e.printStackTrace();
			}
		}
	}
	
	public synchronized void rename(String newName){
		Connection connection = null;
		PreparedStatement statement = null;
		
		try {
			connection = Ublisk.getNewDatabaseConnection("Guilds name change");
			statement = connection.prepareStatement("UPDATE guilds SET name=? WHERE name=?");
			statement.setString(1, newName);
			statement.setString(2, name);
			statement.executeUpdate();
		} catch (SQLException e){
			Logger.log(LogLevel.SEVERE, "Guilds", "Database error while trying to change guild name");
			e.printStackTrace();
		} finally {
			try {
				if (statement != null) statement.close();
				if (connection != null) connection.close();
			} catch (SQLException e){
				e.printStackTrace();
			}
		}
		
		this.name = newName;
	}
	
	public synchronized void setOwner(UPlayer player){
		setOwner(player.getPlayer());
	}
	
	public synchronized void setOwner(OfflinePlayer newOwner){
		Connection connection = null;
		PreparedStatement statement = null;
		
		try {
			connection = Ublisk.getNewDatabaseConnection(this.getName() + " set owner to " + newOwner.getName());
			statement = connection.prepareStatement("UPDATE guilds SET owner=? WHERE name=?");
			statement.setString(1, newOwner.getUniqueId().toString());
			statement.setString(2, this.getName());
			statement.executeUpdate();
		} catch (SQLException e){
			e.printStackTrace();
		} finally {
			try {
				if (statement != null) statement.close();
				if (connection != null) connection.close();
			} catch (SQLException e){
				e.printStackTrace();
			}
		}
		
		Cache.addCachedObject("owner:" + this.getName(), newOwner);
	}

	public synchronized static List<Guild> getGuildsList() {
		Connection connection = null;
		PreparedStatement query = null;
		ResultSet result = null;
		List<String> names = null; 
		try {
			connection = Ublisk.getNewDatabaseConnection("Guilds list");
			query = connection.prepareStatement("SELECT * FROM `guilds` ORDER BY `points`;");
			result = query.executeQuery();
			names = ListUtils.getStringListFromResultSet(result, "name");
		} catch (SQLException e){
			Logger.log(LogLevel.SEVERE, "Unable to connect to database for getting guild list");
			e.printStackTrace();
		} finally {
			try {
				if (result != null) result.close();
				if (query != null) query.close();
				if (connection != null) connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		
		List<Guild> guilds = new ArrayList<Guild>();
		
		for (String name : names){
			Guild guild = new Guild(name);
			guilds.add(guild);
		}
		
		return guilds;
	}
	
	public synchronized static List<Guild> getGuildsList(int limit) {
		Connection connection = null;
		PreparedStatement query = null;
		ResultSet result = null;
		List<String> names = null; 
		try {
			connection = Ublisk.getNewDatabaseConnection("Guilds list");
			query = connection.prepareStatement("SELECT * FROM `guilds` ORDER BY `points` LIMIT ?;");
			query.setInt(1, limit);
			result = query.executeQuery();
			names = ListUtils.getStringListFromResultSet(result, "name");
		} catch (SQLException e){
			Logger.log(LogLevel.SEVERE, "Unable to connect to database for getting guild list");
			e.printStackTrace();
		} finally {
			try {
				if (result != null) result.close();
				if (query != null) query.close();
				if (connection != null) connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		
		List<Guild> guilds = new ArrayList<Guild>();
		
		for (String name : names){
			Guild guild = new Guild(name);
			guilds.add(guild);
		}
		
		return guilds;
	}

	public static class GuildInvite {

		private Guild guild;
		private UPlayer source;

		public GuildInvite(Guild guild, UPlayer source) {
			this.guild = guild;
			this.source = source;
		}

		public Guild getGuild() {
			return guild;
		}

		public UPlayer getSource() {
			return source;
		}

	}
	
	/**
	 * Gets the guild the player is in
	 * @return A guild if player is in a guild, null if the player is not in a guild.
	 */
	public static Guild getGuild(OfflinePlayer player) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		
		String guildName = null;
		try {
			connection = Ublisk.getNewDatabaseConnection("Get player guild");
			statement = connection.prepareStatement("SELECT * FROM `player_info_2` WHERE uuid=?;");
			statement.setString(1, player.getUniqueId().toString());
			result = statement.executeQuery();
			result.next();
			guildName = result.getString("guild");
		} catch (SQLException e){
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {
			try {
				if (result != null) result.close();
				if (statement != null) statement.close();
				if (connection != null) connection.close();
			} catch (SQLException e){
				throw new RuntimeException(e.getMessage());
			}
		}
		
		if (guildName == null) return null;
		
		if (guildName.equalsIgnoreCase("None")) return null;
		
		Guild guild = new Guild(guildName);
		
		if (guild.exists()){
			return guild;
		} else {
			return null;
		}
	}
	
	public static void leaveGuild(OfflinePlayer player){
		Guild guild = getGuild(player);	
		if (guild == null){
			throw new UnsupportedOperationException("Player is not in a guild");
		}
		
		if (!guild.exists()){
			throw new UnsupportedOperationException("The player's guild no longer exists");
		}
		
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = Ublisk.getNewDatabaseConnection("Leave guild");
			statement = connection.prepareStatement("UPDATE " + PlayerInfo.TABLE_NAME + " SET guild='None' WHERE uuid=?;");
			statement.setString(1, player.getUniqueId().toString());
			statement.executeUpdate();
		} catch (SQLException e){
			throw new RuntimeException(e.getMessage());
		} finally {
			try {
				if (statement != null) statement.close();
				if (connection != null) connection.close();
			} catch (SQLException e){
				throw new RuntimeException(e.getMessage());
			}
		}
	}
	
	public static void setGuild(Guild guild, OfflinePlayer player){
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = Ublisk.getNewDatabaseConnection("Set guild");
			statement = connection.prepareStatement("UPDATE " + PlayerInfo.TABLE_NAME + " SET guild=? WHERE uuid=?;");
			statement.setString(1, guild.getName());
			statement.setString(2, player.getUniqueId().toString());
			statement.executeUpdate();
		} catch (SQLException e){
			throw new RuntimeException(e.getMessage());
		} finally {
			try {
				if (statement != null) statement.close();
				if (connection != null) connection.close();
			} catch (SQLException e){
				throw new RuntimeException(e.getMessage());
			}
		}
	}

}
