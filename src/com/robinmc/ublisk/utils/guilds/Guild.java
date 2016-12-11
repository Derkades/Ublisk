package com.robinmc.ublisk.utils.guilds;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.configuration.file.YamlConfiguration;

import com.robinmc.ublisk.Var;
import com.robinmc.ublisk.utils.Logger;
import com.robinmc.ublisk.utils.Logger.LogLevel;
import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.UUIDUtils;
import com.robinmc.ublisk.utils.Ublisk;
import com.robinmc.ublisk.utils.exception.NotInGuildException;

public class Guild {
	
	private String name;
	
	Guild(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	public File getFile(){
		return new File(Guilds.PATH, getName() + ".yml");
	}
	
	private YamlConfiguration config;
	
	private void openConfig(){
		//Logger.log(LogLevel.DEBUG, "Initialize config");
		if (getFile().exists()){
			config = YamlConfiguration.loadConfiguration(getFile());
			//Logger.log(LogLevel.DEBUG, "Load existing config");
		} else {
			config = new YamlConfiguration();
			//Logger.log(LogLevel.DEBUG, "Create new config");
		}
	}
	
	private void closeConfig(){
		//Logger.log(LogLevel.DEBUG, "Close config");
		try {
			config.save(getFile());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			config = null;
		}
	}
	
	public void addPlayer(UPlayer player){
		//Logger.log(LogLevel.DEBUG, "Add player");
		openConfig();
		if (config.contains("players") && config.isSet("players")){
			//Logger.log(LogLevel.DEBUG, "Config contains list");
			List<String> list = config.getStringList("players");		
			list.add(player.getUniqueId().toString());
			Logger.log(LogLevel.DEBUG, "List: " + list);
			config.set("players", list);
		} else {
			//Logger.log(LogLevel.DEBUG, "Config does not contain list");
			List<String> list = new ArrayList<String>();
			list.add(player.getUniqueId().toString());
			//Logger.log(LogLevel.DEBUG, "List: " + list);
			config.set("players", list);
		}
		closeConfig();
	}
	
	public boolean hasPlayer(UPlayer player){
		//Logger.log(LogLevel.DEBUG, "Has player?");
		openConfig();
		boolean bool = config.getStringList("players").contains(player.getUniqueId().toString());
		//Logger.log(LogLevel.DEBUG, "Has player: " + bool);
		closeConfig();
		return bool;
	}	
	
	public void removePlayer(UPlayer player) throws NotInGuildException {
		//Logger.log(LogLevel.DEBUG, "Remove player");
		openConfig();
		List<String> list = config.getStringList("players");
		//Logger.log(LogLevel.DEBUG, "List: " + list);
		if (!list.contains(player.getUniqueId().toString())){
			//Logger.log(LogLevel.DEBUG, "List does not contain player");
			throw new NotInGuildException();
		}
		//Logger.log(LogLevel.DEBUG, "List contains player");
		list.remove(player.getUniqueId().toString());
		//If list is empty, delete guild
		if (list.isEmpty()){
			//Logger.log(LogLevel.DEBUG, "List is empty, guild will be deleted");
			delete();
			config = null;
			return; //Return now, we do not want to save the config.
		}
		config.set("players", list);
		closeConfig();
	}
	
	public void delete(){
		//Logger.log(LogLevel.DEBUG, "File delete");
		getFile().delete();
	}
	
	public boolean hasReachedPlayerLimit(){
		openConfig();
		boolean bool;
		if (config.contains("players") && config.isSet("players")){
			List<String> list = config.getStringList("players");
			bool = list.size() >= Var.GUILD_MEMBER_LIMIT;
		} else {
			bool = false;
		}
		closeConfig();
		return bool;
	}
	
	public List<String> getPlayerUUIDList(){
		openConfig();
		List<String> list;
		if (config.contains("players") && config.isSet("players")){
			list = config.getStringList("players");
		} else {
			list = new ArrayList<String>();
		}
		closeConfig();
		return list;
	}
	
	public List<String> getPlayerNamesList(){
		List<String> uuids = getPlayerUUIDList();
		List<String> names = new ArrayList<String>();
		for (String uuid : uuids){
			UUIDUtils.getNameFromId(UUID.fromString(uuid));
		}
		return names;
	}
	
	public int getPlayerCount(){
		List<String> list = getPlayerUUIDList();
		if (list.isEmpty()) return 0;
		return list.size();
	}
	
	public int getPoints(){
		openConfig();
		int points;
		if (config.contains("points")){
			points = config.getInt("points");
		} else {
			points = 0;
		}
		closeConfig();
		return points;
	}
	
	public void setPoints(int points){
		openConfig();
		config.set("points", points);
		closeConfig();
	}
	
	public boolean hasPoints(int points){
		return getPoints() >= points;
	}
	
	public void setImageURL(String url){
		openConfig();
		config.set("image", url);
		closeConfig();
	}
	
	public String getImageURL(){
		openConfig();
		String url;
		if (config.isSet("image")){
			url = config.getString("image");
		} else {
			url = "http://images.robinmc.com/upload/no-guild-image.png";
		}
		
		closeConfig();
		return url;
	}
	
	/*
	 * Database structure:
	 * TEXT name (Guild name)
	 * INT points (Guild points)
	 * TEXT img (Image URL)
	 * INT playercount (Player count)
	 * TEXT players (player1 name, player2 name, ...)
	 */
	public void syncInfoWithDatabase() throws SQLException {
		Logger.log(LogLevel.INFO, "Guilds", "Synchornising guild data for " + this.getName());
		boolean containsGuild = containsGuild();			
		if (containsGuild){
			Connection connection = null;
			PreparedStatement update = null;
			try {
				connection = Ublisk.getNewDatabaseConnection("Guilds (update)");
				update = connection.prepareStatement("UPDATE `guilds` SET points=?,img=?,playercount=?,players=? WHERE name=?;");
	    		
	    		update.setInt(1, this.getPoints());
	    		update.setString(2, this.getImageURL());
	    		update.setInt(3, this.getPlayerCount());
	    		update.setString(4, String.join(", ", this.getPlayerNamesList()));
	    		
	    		update.setString(5, this.getName());
	    		
	    		update.executeUpdate();
			} catch (SQLException e){
				throw e;
			} finally {
				update.close();
				connection.close();
			}
		} else {
			Connection connection = null;
			PreparedStatement insert = null;
			try {
				connection = Ublisk.getNewDatabaseConnection("Guilds (insert)");
				insert = connection.prepareStatement("INSERT INTO `guilds` values(?, ?, ?, ?, ?);");
				insert.setString(1, this.getName());
				insert.setInt(2, this.getPoints());
				insert.setString(3, this.getImageURL());
				insert.setInt(4, this.getPlayerCount());
				insert.setString(5, String.join(", ", this.getPlayerNamesList()));
	    		
	    		insert.execute();
			} catch (SQLException e){
				throw e;
			} finally {
				insert.close();
				connection.close();
			}
		}

	}
	
	private boolean containsGuild() throws SQLException {
		Connection connection = null;
		
		PreparedStatement sql = null;
		ResultSet result = null;
		
		boolean containsGuild = false;
		
		try {
			connection = Ublisk.getNewDatabaseConnection("Contains guild");
			sql = connection.prepareStatement("SELECT * FROM `guilds` WHERE name=?;");
			sql.setString(1, this.getName());
			result = sql.executeQuery();
			containsGuild = result.next();
		} catch (SQLException e){
			throw e;
		} finally {
			sql.close();
			result.close();
			
			connection.close();
		}
		
		return containsGuild;
	}

}
