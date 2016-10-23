package com.robinmc.ublisk.utils.guilds;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;

import com.robinmc.ublisk.Var;
import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.exception.NotInGuildException;
import com.robinmc.ublisk.utils.logging.LogLevel;
import com.robinmc.ublisk.utils.logging.Logger;

public class Guild {
	
	private String name;
	
	Guild(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	public File getFile(){
		return new File(Guilds.path, getName() + ".yml");
	}
	
	private YamlConfiguration config;
	
	private void openConfig(){
		Logger.log(LogLevel.DEBUG, "Initialize config");
		if (getFile().exists()){
			config = YamlConfiguration.loadConfiguration(getFile());
			Logger.log(LogLevel.DEBUG, "Load existing config");
		} else {
			config = new YamlConfiguration();
			Logger.log(LogLevel.DEBUG, "Create new config");
		}
	}
	
	private void closeConfig(){
		Logger.log(LogLevel.DEBUG, "Close config");
		try {
			config.save(getFile());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			config = null;
		}
	}
	
	public void addPlayer(UPlayer player){
		Logger.log(LogLevel.DEBUG, "Add player");
		openConfig();
		if (config.contains("players") && config.isSet("players")){
			Logger.log(LogLevel.DEBUG, "Config contains list");
			List<String> list = config.getStringList("players");		
			list.add(player.getUniqueId().toString());
			Logger.log(LogLevel.DEBUG, "List: " + list);
			config.set("players", list);
		} else {
			Logger.log(LogLevel.DEBUG, "Config does not contain list");
			List<String> list = new ArrayList<String>();
			list.add(player.getUniqueId().toString());
			Logger.log(LogLevel.DEBUG, "List: " + list);
			config.set("players", list);
		}
		closeConfig();
	}
	
	public boolean hasPlayer(UPlayer player){
		Logger.log(LogLevel.DEBUG, "Has player?");
		openConfig();
		boolean bool = config.getStringList("players").contains(player.getUniqueId().toString());
		Logger.log(LogLevel.DEBUG, "Has player: " + bool);
		closeConfig();
		return bool;
	}	
	
	public void removePlayer(UPlayer player) throws NotInGuildException {
		Logger.log(LogLevel.DEBUG, "Remove player");
		openConfig();
		List<String> list = config.getStringList("players");
		Logger.log(LogLevel.DEBUG, "List: " + list);
		if (!list.contains(player.getUniqueId().toString())){
			Logger.log(LogLevel.DEBUG, "List does not contain player");
			throw new NotInGuildException();
		}
		Logger.log(LogLevel.DEBUG, "List contains player");
		list.remove(player.getUniqueId().toString());
		//If list is empty, delete guild
		if (list.isEmpty()){
			Logger.log(LogLevel.DEBUG, "List is empty, guild will be deleted");
			delete();
			config = null;
			return; //Return now, we do not want to save the config.
		}
		config.set("players", list);
		closeConfig();
	}
	
	public void delete(){
		Logger.log(LogLevel.DEBUG, "File delete");
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

}
