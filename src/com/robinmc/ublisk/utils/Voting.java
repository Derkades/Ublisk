package com.robinmc.ublisk.utils;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.robinmc.ublisk.Var;
import com.robinmc.ublisk.utils.java.NumberUtils;

public class Voting {

	private static Location oldPlayerLocation;
	private static boolean playerOpeningBox = false;

	public static void openVotingBox(Player player) {
		setPlayerOpeningBox(true);
		oldPlayerLocation = player.getLocation();
		player.teleport(new Location(Var.WORLD, 3.5, 71, -62.5, 0, 0));
	}

	public static int getRandomXP() {
		return NumberUtils.randomInteger(Var.VOTE_XP_MIN, Var.VOTE_XP_MAX);
	}

	public static int getRandomGold() {
		return NumberUtils.randomInteger(Var.VOTE_GOLD_MIN, Var.VOTE_GOLD_MAX);
	}

	public static int getRandomLife() {
		return NumberUtils.randomInteger(Var.VOTE_LIFE_MIN, Var.VOTE_LIFE_MAX);
	}

	public static boolean isVotingChest(Block block) {
		Location loc = block.getLocation();
		return loc.getBlockX() == 3 && loc.getBlockY() == 71 && loc.getBlockZ() == -55;
	}

	public static Location getOldPlayerLocation() {
		return oldPlayerLocation;
	}

	public static boolean isPlayerOpeningBox() {
		return playerOpeningBox;
	}

	public static void setPlayerOpeningBox(boolean playerOpeningBox) {
		Voting.playerOpeningBox = playerOpeningBox;
	}

}
