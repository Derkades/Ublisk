package xyz.derkades.ublisk.utils;

import java.net.URL;
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
		
		ResultSet result = null;
		boolean contains = true;
		try (PreparedStatement query = Ublisk.prepareStatement("Guilds check (" + name + ")", "SELECT * FROM `guilds` WHERE name=?")){
			query.setString(1, name);
			result = query.executeQuery();
			contains = result.next();
		} catch (SQLException e) {
			Ublisk.exception(e, getClass());
		}
		
		Cache.addCachedObject("exists:" + this.getName(), contains, 300);
		
		return contains;
	}

	public String getName() {
		return name;
	}

	/**
	 * @param owner Guild owner. Can be offline
	 * @throws IllegalArgumentException If the owner is null or an empty string.
	 * @throws UnsupportedOperationException If a guild with that name already exists
	 */
	public synchronized void create(final UPlayer owner) throws IllegalArgumentException, UnsupportedOperationException {
		if (this.exists())
			throw new UnsupportedOperationException("A guild with this name already exists.");

		if (owner == null)
			throw new IllegalArgumentException("Owner cannot be null");
		
		try (PreparedStatement insert = Ublisk.prepareStatement("Create guild " + this.getName(), 
				"INSERT INTO `guilds` (name, owner) values(?, ?);")){
			insert.setString(1, this.getName());
			insert.setString(2, owner.getUniqueId().toString());
			insert.execute();
		} catch (SQLException e) {
			Ublisk.exception(e, Guild.class);
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
		
		int points = 0;
		try (PreparedStatement select = Ublisk.prepareStatement("Guild points " + this.getName(), 
				"SELECT `points` FROM `guilds` WHERE name=?", this.getName());
				ResultSet result = select.executeQuery()){
			result.next();
			points = result.getInt("points");
		} catch (SQLException e){
			Ublisk.exception(e, Guild.class);
		}
		
		Cache.addCachedObject("points:" + this.getName(), points, 300);
		
		return points;
	}
	
	public synchronized void setPoints(int points){		
		try (PreparedStatement statement = Ublisk.prepareStatement("Guild points " + this.getName(), 
				"UPDATE `guilds` SET points=? WHERE name=?",
				points, this.getName())){
			statement.executeUpdate();
		} catch (SQLException e){
			Ublisk.exception(e, Guild.class);
		}
		
		Cache.addCachedObject("points:" + this.getName(), points, 300);
	}
	
	/**
	 * This will also send a message to all guild members.
	 * @param points
	 * @param playerName Name of the player who got the points for the guild
	 */
	public synchronized void addPoints(int points, String playerName){
		try (PreparedStatement statement = Ublisk.prepareStatement("Add points " + this.getName(), 
				"UPDATE `guilds` SET points=points+? WHERE name=?",
				points, this.getName())){
			statement.executeUpdate();
		} catch (SQLException e){
			Ublisk.exception(e, Guild.class);
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
		
		try (PreparedStatement statement = Ublisk.prepareStatement("Get guild members " + this.getName(), 
				"SELECT uuid FROM player_info_2 WHERE guild=?", this.getName());
				ResultSet result = statement.executeQuery()) {
			while (result.next()){
				UUID uuid = UUID.fromString(result.getString("uuid"));
				OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
				list.add(new UPlayer(player));
			}
		} catch (SQLException e){
			Ublisk.exception(e, Guild.class);
		}

		return list;
	}
	
	public synchronized void remove(){
		try (PreparedStatement delete = Ublisk.prepareStatement("Delete " + this.getName(), 
				"DELETE FROM `guilds` WHERE name=?", this.getName())){
			delete.execute();
		} catch (SQLException e){
			Ublisk.exception(e, Guild.class);
		}
		
		Cache.addCachedObject("exists:" + this.getName(), false, 600);
	}
	
	public synchronized OfflinePlayer getOwner(){
		Object cache = Cache.getCachedObject("owner:" + this.getName());
		if (cache != null){
			return (OfflinePlayer) cache;
		}

		String uuid = null;
		try (PreparedStatement statement = Ublisk.prepareStatement("Get guild owner " + this.getName(),
				"SELECT `owner` FROM `guilds` WHERE name=?", this.getName());
				ResultSet result = statement.executeQuery()){
			result.next();
			uuid = result.getString("owner");
		} catch (SQLException e){
			Ublisk.exception(e, Guild.class);
		}
		
		OfflinePlayer owner = Bukkit.getOfflinePlayer(UUID.fromString(uuid));
		
		Cache.addCachedObject("owner:" + this.getName(), owner, 1000);
		
		return owner;
	}
	
	public synchronized void setDescription(String description){
		Cache.addCachedObject("description:" + this.getName(), description, 300);
		
		try (PreparedStatement statement = Ublisk.prepareStatement("Guilds description set " + this.getName(), 
				"UPDATE guilds SET description=? WHERE name=?", description, name)){
			statement.executeUpdate();
		} catch (SQLException e){
			Logger.log(LogLevel.SEVERE, "Guilds", "Database error while trying to set description");
			e.printStackTrace();
		}
	}
	
	public synchronized String getDescription(){
		Object cache = Cache.getCachedObject("description:" + this.getName());
		if (cache != null){
			return (String) cache;
		}
		
		String description = null;
		try (PreparedStatement statement = Ublisk.prepareStatement("Get description " + this.getName(),
				"SELECT description FROM guilds WHERE name=?", this.getName());
				ResultSet result = statement.executeQuery()){
			result.next();
			description = result.getString("description");
		} catch (SQLException e){
			Ublisk.exception(e, Guild.class);
		}
		
		Cache.addCachedObject("description:" + this.getName(), description, 600);
		
		return description;
	}
	
	public synchronized void setIconURL(URL icon){	
		try (PreparedStatement statement = Ublisk.prepareStatement("Guilds icon update " + this.getName(),
				"UPDATE guilds SET icon=? WHERE name=?", icon.toString(), name)) {
			statement.executeUpdate();
		} catch (SQLException e){
			Ublisk.exception(e, Guild.class);
		}
	}
	
	/**
	 * Changes guild name. Check if there is another guild with this name before calling this method.
	 * @param newName
	 */
	public synchronized void rename(String newName){
		try (PreparedStatement statement = Ublisk.prepareStatement("Name change " + this.getName(), 
				"UPDATE guilds SET name=? WHERE name=?", newName, this.getName())){
			statement.executeUpdate();
		} catch (SQLException e){
			Ublisk.exception(e, Guild.class);
		}
		
		this.name = newName;
	}
	
	public synchronized void setOwner(UPlayer newOwner){
		try (PreparedStatement statement = Ublisk.prepareStatement("Owner change " + this.getName(), 
				"UPDATE guilds SET owner=? WHERE name=?", newOwner.getUniqueId().toString(), this.getName())){
			statement.executeUpdate();
		} catch (SQLException e){
			Ublisk.exception(e, Guild.class);
		}
		
		Cache.addCachedObject("owner:" + this.getName(), newOwner);
	}

	public synchronized static List<Guild> getGuildsList() {
		List<String> names = null; 
		
		try (PreparedStatement statement = Ublisk.prepareStatement("Guild list ",
				"SELECT * FROM guilds ORDER BY points");
				ResultSet result = statement.executeQuery()){
			names = ListUtils.getStringListFromResultSet(result, "name");
		} catch (SQLException e){
			Ublisk.exception(e, Guild.class);
		}	
		
		List<Guild> guilds = new ArrayList<Guild>();
		
		for (String name : names){
			Guild guild = new Guild(name);
			guilds.add(guild);
		}
		
		return guilds;
	}
	
	public synchronized static List<Guild> getGuildsList(int limit) {
		List<String> names = null; 
		
		try (PreparedStatement statement = Ublisk.prepareStatement("Guild list ",
				"SELECT * FROM guilds ORDER BY points LIMIT ?", limit);
				ResultSet result = statement.executeQuery()){
			names = ListUtils.getStringListFromResultSet(result, "name");
		} catch (SQLException e){
			Ublisk.exception(e, Guild.class);
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
	public static Guild getGuild(UPlayer player) {
		String guildName = null;
		try (PreparedStatement statement = Ublisk.prepareStatement("Get guild for " + player.getName(),
				"SELECT * FROM " + PlayerInfo.TABLE_NAME + " WHERE uuid=?", player.getUniqueId().toString());
				ResultSet result = statement.executeQuery()) {
			result.next();
			guildName = result.getString("guild");
		} catch (SQLException e){
			Ublisk.exception(e, Guild.class);
		}
		
		if (guildName == null || guildName.equalsIgnoreCase("None")) return null;
		
		Guild guild = new Guild(guildName);
		
		if (guild.exists()){
			return guild;
		} else {
			return null;
		}
	}
	
	public static void leaveGuild(UPlayer player){
		Guild guild = getGuild(player);
		
		if (guild == null){
			throw new UnsupportedOperationException("Player is not in a guild");
		}
		
		if (!guild.exists()){
			throw new UnsupportedOperationException("The player's guild longer exists");
		}
		
		try (PreparedStatement statement = Ublisk.prepareStatement("Leave guild " + player.getName(),
				"UPDATE " + PlayerInfo.TABLE_NAME + " SET guild='None' WHERE uuid=?", player.getUniqueId().toString())){
			statement.executeUpdate();
		} catch (SQLException e){
			Ublisk.exception(e, Guild.class);
		}
	}
	
	public static void setGuild(Guild guild, OfflinePlayer player){
		try (PreparedStatement statement = Ublisk.prepareStatement("Set guild " + player.getName(), 
				"UPDATE " + PlayerInfo.TABLE_NAME + " SET guild=? WHERE uuid=?", guild.getName(), player.getUniqueId().toString())){
			statement.executeUpdate();
		} catch (SQLException e){
			Ublisk.exception(e, Guild.class);
		}
	}

}
