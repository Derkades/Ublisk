package com.robinmc.ublisk;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

import com.robinmc.ublisk.utils.DataFile;

public class Var {
	
	/**
	 * Ublisk main world. Do not change unless you know what you are doing.
	 */
	public static final World WORLD = Bukkit.getWorld("ublisk");
	
	/**
	 * Direct URL to resource pack zip. Do not change unless you know what you are doing.
	 */
	public static final String PACK_URL = "http://ublisk.robinmc.com/texture/UbliskTextures37.zip";
	
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
	
	public static final int VOTE_XP_MIN = 20;
	public static final int VOTE_XP_MAX = 100;
	
	public static final int VOTE_GOLD_MIN = 0;
	public static final int VOTE_GOLD_MAX = 50;
	
	public static final int VOTE_LIFE_MIN = 0;
	public static final int VOTE_LIFE_MAX = 2;
	
	/**
	 * List of materials that are on top of a solid block and which mobs should spawn inside of
	 */
	public static final List<Material> MOB_SPAWNING_AIR_BLOCKS = Arrays.asList(
			Material.AIR, 
			Material.LONG_GRASS,
			Material.CHORUS_FLOWER,
			Material.YELLOW_FLOWER,
			Material.TORCH,
			Material.SUGAR_CANE_BLOCK
			);
	
	/**
	 * Do not spawn mobs on top of these blocks
	 */
	public static final List<Material> MOB_SPAWNING_CANCEL = Arrays.asList(
			Material.FENCE,
			Material.FENCE_GATE,
			Material.COBBLE_WALL,
			Material.WATER,
			Material.STATIONARY_WATER,
			Material.LEAVES,
			Material.LEAVES_2
			);
	
	/**
	 * Locations of trapdoors players should be able to open
	 */
	public static final List<Location> SAFE_TRAPDOORS = Arrays.asList(
			new Location(Var.WORLD, -23, 74, 3),
			new Location(Var.WORLD, 132, 81, 289)
			);
	
	/**
	 * Number of seconds after which a player will be automatically set as AFK.
	 */
	public static final int AFK_TIME = 60;
	
	/**
	 * Number of half harts a player will have at a level. For example,
	 * 5, 6
	 * Will mean that the player has 6 / 2 = 3 hearts at level 5.
	 */
	public static final HashMap<Integer, Integer> LEVEL_HEALTH = HashMaps.build(
			1, 3,
			2, 4
			);
	
	public static final String DATABASE_HOST = "192.168.0.121";
	public static final int DATABASE_PORT = 3306;
	public static final String DATABASE_USER = DataFile.MYSQL.getConfig().getString("user");
	public static final String DATABASE_PASSWORD = DataFile.MYSQL.getConfig().getString("password"); //Admit it, you hoped the password would be here in plain text. Nope!
	public static final String DATABASE_DB_NAME = "ublisk";

}
