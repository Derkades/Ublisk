package com.robinmc.ublisk.utils;

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

import com.robinmc.ublisk.DataFile;
import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.database.PlayerInfo;
import com.robinmc.ublisk.utils.Logger.LogLevel;
import com.robinmc.ublisk.utils.java.ListUtils;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

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
				query.close();
				resultSet.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return contains;
	}

	public String getName() {
		return name;
	}

	/**
	 * 
	 * @param owner
	 *        The name of the guild owner, for example "RobinMC"
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
			insert.setString(2, owner.getName());
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
		Connection connection = null;
		PreparedStatement query = null;
		ResultSet result = null;
		
		int points = 0;
		try {
			connection = Ublisk.getNewDatabaseConnection("Guild points " + this.getName());
			query = connection.prepareStatement("SELECT `points` FROM `guilds` WHERE name=?");
			query.setString(1, this.getName());
			result = query.executeQuery();
			result.next();
			points = result.getInt("points");
		} catch (SQLException e){
			Logger.log(LogLevel.SEVERE, "Guilds", "Database error while trying to get guild points for " + this.getName());
			e.printStackTrace();
		} finally {
			try {
				result.close();
				query.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return points;
	}
	
	public synchronized void setPoints(int points){
		Connection connection = null;
		PreparedStatement query = null;
		try {
			connection = Ublisk.getNewDatabaseConnection("Guild points " + this.getName());
			query = connection.prepareStatement("UPDATE `guilds` SET points=? WHERE name=?;");
			query.setInt(1, points);
			query.setString(2, this.getName());
			query.executeUpdate();
		} catch (SQLException e){
			Logger.log(LogLevel.SEVERE, "Guilds", "Database error while trying to set guild points for " + this.getName());
			e.printStackTrace();
		} finally {
			try {
				query.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public List<OfflinePlayer> getMembers(){
		/*List<OfflinePlayer> list = new ArrayList<OfflinePlayer>();
		for (String key : DataFile.GUILDS.getConfig().getConfigurationSection("guild").getKeys(false)){
			if (DataFile.GUILDS.getConfig().getString("guild." + key).equalsIgnoreCase(this.getName())){
				UUID uuid = UUID.fromString(key);
				list.add(Bukkit.getOfflinePlayer(uuid));
			}
		}
		return list;*/
		
		
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
				delete.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
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
				result.close();
				query.close();
				connection.close();
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
				result.close();
				query.close();
				connection.close();
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
			statement = connection.prepareStatement("SELECT guild FROM " + PlayerInfo.TABLE_NAME + " WHERE uuid=?;");
			statement.setString(1, player.getUniqueId().toString());
			result = statement.executeQuery();
			guildName = result.getString("guild");
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
		
		if (guildName == null) return null;
		
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
