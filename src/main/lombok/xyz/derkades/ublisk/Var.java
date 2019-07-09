package xyz.derkades.ublisk;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class Var {
	
	public static boolean DEBUG = false;
	
	/**
	 * Ublisk main world. Do not change unless you know what you are doing.
	 */
	public static final World WORLD = Bukkit.getWorld("ublisk");
	
	/**
	 * Double XP cooldown, in minutes
	 */
	public static final int DOUBLE_XP_COOLDOWN = 10;
	
	/**
	 * Vote URL
	 */
	public static final String VOTE_URL = "http://robinmc.com/hi"; // XXX Vote URL
	
	/**
	 * Maximum amount of players in a guild
	 */
	public static final int GUILD_MEMBER_LIMIT = 8;
	
	/**
	 * Prefix character for chat triggers 
	 */
	public static final char TRIGGER_CHARACTER = '!';
	
	/**
	 * Additional delay between trackers.
	 * 
	 * Tracker delay = TRACKER_DELAY + [player count] * 50
	 */
	public static final int TRACKER_DELAY = 40;
	
	/**
	 * Locations of trapdoors players should be able to open
	 */
	public static final List<Location> SAFE_TRAPDOORS = Arrays.asList(
			new Location(Var.WORLD, -23, 74, 3),
			new Location(Var.WORLD, 132, 81, 289)
			);
	
	public static final boolean BLOCK_REGENERATION_ENABLED = false;
	
	public static final String DATABASE_HOST = DataFile.MYSQL.getConfig().getString("ip");
	public static final int DATABASE_PORT = 3306;
	public static final String DATABASE_USER = DataFile.MYSQL.getConfig().getString("user");
	public static final String DATABASE_PASSWORD = DataFile.MYSQL.getConfig().getString("password"); //Admit it, you hoped the password would be here in plain text. Nope!
	public static final String DATABASE_DB_NAME = DataFile.MYSQL.getConfig().getString("database");
	
	public static final String WEBSITE_ROOT = "https://ublisk.derkades.xyz/";
	
	public static final String GITHUB_ACCESS_TOKEN = DataFile.MYSQL.getConfig().getString("github.token");

}
