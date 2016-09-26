package com.robinmc.ublisk.utils.variable;

import org.bukkit.Bukkit;
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
	public static final int DOUBLE_XP_TIME = 60; // XXX: Comfirm that this actually works
	
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

}
