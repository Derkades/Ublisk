package com.robinmc.ublisk.utils;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.scheduler.BukkitRunnable;

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
	VOTING("voting"),
	MONEY("money"),
	LAST_PLAYED("last-played"),
	PERMISSIONS("permissions"),
	MYSQL("mysql"),
	TOWN("town"),
	GUILDS("guilds");

	private File file;

	DataFile(String name) {
		this.file = new File(Main.getInstance().getDataFolder() + File.separator + "data", name + ".yml");
	}

	private YamlConfiguration config;

	public void reload() {
		config = YamlConfiguration.loadConfiguration(file);
	}

	public YamlConfiguration getConfig() {
		if (config == null)
			reload();
		return config;
	}

	public void save() {
		try {
			getConfig().save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Deprecated
	public void set(String path, Object o) {
		getConfig().set(path, o);
	}

	@Deprecated
	public String getString(String path) {
		return getConfig().getString(path);
	}

	@Deprecated
	public boolean getBoolean(String path) {
		return getConfig().getBoolean(path);
	}

	@Deprecated
	public int getInteger(String path) {
		return getConfig().getInt(path);
	}

	@Deprecated
	public double getDouble(String path) {
		return getConfig().getDouble(path);
	}

	@Deprecated
	public List<String> getStringList(String path) {
		return getConfig().getStringList(path);
	}

	@Deprecated
	public boolean isSet(String path) {
		return getConfig().isSet(path);
	}
	
	public static class SaveFiles extends BukkitRunnable {
		
		public void run(){
			for (DataFile dataFile : DataFile.values()){
				dataFile.save();
			}
		}
		
	}

}
