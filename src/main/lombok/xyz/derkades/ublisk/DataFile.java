package xyz.derkades.ublisk;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.scheduler.BukkitRunnable;

public enum DataFile {

	QUESTS("quests"),
	QUESTS_2("quests2"),
	SETTINGS("settings"),
	FRIENDS("friends"),
	IP("ip"),
	LIFE_CRYSTAL("life"),
	VOTING("voting"),
	MONEY("money"),
	LAST_PLAYED("last-played"),
	PERMISSIONS("permissions"),
	MYSQL("mysql"),
	TOWN("town"),
	PLAYER_LOCATION("playerloc");

	private File file;

	DataFile(final String name) {
		this.file = new File(Main.getInstance().getDataFolder() + File.separator + "data", name + ".yml");
	}

	private YamlConfiguration config;

	public void reload() {
		this.config = YamlConfiguration.loadConfiguration(this.file);
	}

	public YamlConfiguration getConfig() {
		if (this.config == null)
			this.reload();
		return this.config;
	}

	public void save() {
		try {
			this.getConfig().save(this.file);
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	public static class SaveFiles extends BukkitRunnable {

		@Override
		public void run(){
			for (final DataFile dataFile : DataFile.values()){
				dataFile.save();
			}
		}

	}

}
