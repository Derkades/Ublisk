package com.robinmc.ublisk.utils.variable;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;

public class Var {
	
	/**
	 * Ublisk main world
	 */
	public static final World WORLD = Bukkit.getWorld("ublisk");
	
	/**
	 * Direct URL to resource pack zip
	 */
	public static final String PACK_URL = "http://ublisk.robinmc.com/texture/UbliskTextures6.zip";
	
	/**
	 * How long double XP will last for.
	 */
	public static final int DOUBLE_XP_TIME = 60; // XXX: Confirm that this actually works
	
	/**
	 * <b>The number all XP is divided by.</b>
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
	public static final String VOTE_URL = "http://robinmc.com/hi"; // TODO Vote URL
	
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
			Material.SUGAR_CANE_BLOCK);
	
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
			Material.LEAVES_2);

}
