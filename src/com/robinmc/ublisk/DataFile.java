package com.robinmc.ublisk;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.scheduler.BukkitRunnable;

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
	PLAYER_LOCATION("playerloc");

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
	
	public static class SaveFiles extends BukkitRunnable {
		
		public void run(){
			for (DataFile dataFile : DataFile.values()){
				dataFile.save();
			}
		}
		
	}

}
