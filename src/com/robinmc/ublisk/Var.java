package com.robinmc.ublisk;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class Var {
	
	public static final boolean DEBUG = true;
	
	/**
	 * Ublisk main world. Do not change unless you know what you are doing.
	 */
	public static final World WORLD = Bukkit.getWorld("ublisk");
	
	/**
	 * <b>The integer all XP is divided by.</b>
	 * <br><br>
	 * Example (if XP_DIVISION is 5): 
	 * <br>
	 * Player has 80 xp
	 * <br>
	 * 80 / 5 = 16
	 * <br>
	 * Actual player xp will be 16
	 */
	public static final int XP_DIVISION = 20;
	
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
	
	/**
	 * Number of half harts a player will have at a level. For example,
	 * 5, 6
	 * Will mean that the player has 6 / 2 = 3 hearts at level 5.
	 */
	@Deprecated
	public static final HashMap<Integer, Integer> LEVEL_HEALTH = HashMaps.build(
			0, 1,
			1, 4,
			2, 5,
			3, 6,
			4, 7,
			5, 7,
			6, 8,
			7, 8,
			8, 9,
			10, 10,
			11, 10,
			12, 11,
			13, 11,
			14, 12,
			15, 12,
			16, 13,
			17, 13,
			18, 14,
			19, 14,
			20, 15
			);
	
	public static final boolean BLOCK_REGENERATION_ENABLED = false;
	
	public static final String DATABASE_HOST = DataFile.MYSQL.getConfig().getString("ip");
	public static final int DATABASE_PORT = 3306;
	public static final String DATABASE_USER = DataFile.MYSQL.getConfig().getString("user");
	public static final String DATABASE_PASSWORD = DataFile.MYSQL.getConfig().getString("password"); //Admit it, you hoped the password would be here in plain text. Nope!
	public static final String DATABASE_DB_NAME = DataFile.MYSQL.getConfig().getString("database");
	
	public static final String WEBSITE_ROOT = "https://ublisk.derkades.xyz/";
	
	//public static final String GITHUB_LOGIN = DataFile.MYSQL.getConfig().getString("github.login");
	public static final String GITHUB_ACCESS_TOKEN = DataFile.MYSQL.getConfig().getString("github.token");

}
