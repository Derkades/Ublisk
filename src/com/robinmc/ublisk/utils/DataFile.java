package com.robinmc.ublisk.utils;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;

import com.robinmc.ublisk.Main;

public enum DataFile {
	
	QUESTS("quests"),
	UUID("uuid"),
	SETTINGS("settings"),
	FRIENDS("friends"),
	CLASSES("classes"),
	IP("ip"),
	LIFE_CRYSTAL("life"),
	XP("xp"),
	VOTING("voting");
	
	private File file;
	
	DataFile(String name){
		this.file = new File(Main.getInstance().getDataFolder() + "\\data", name + ".yml");
	}
	
	private YamlConfiguration config;
	
	public void reload(){
		config = YamlConfiguration.loadConfiguration(file);
	}
	
	public YamlConfiguration getConfig(){
		if (config == null) reload();
		return config;
	}
	
	private void save(){
		try {
			getConfig().save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void set(String path, Object o){
		getConfig().set(path, o);
		save();
	}
	
	public String getString(String path){
		return getConfig().getString(path);
	}
	
	public boolean getBoolean(String path){
		return getConfig().getBoolean(path);
	}
	
	public int getInteger(String path){
		return getConfig().getInt(path);
	}
	
	public double getDouble(String path){
		return getConfig().getDouble(path);
	}
	
	public List<String> getStringList(String path){
		return getConfig().getStringList(path);
	}
	
}
