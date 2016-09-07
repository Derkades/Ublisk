package com.robinmc.ublisk.utils;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.robinmc.ublisk.utils.java.NumberUtils;
import com.robinmc.ublisk.utils.variable.Var;

public class Voting {
	
	private static Location oldPlayerLocation;
	private static boolean playerOpeningBox = false;
	
	public static int getVotingPoints(Player player){
		String path = "voting." + player.getUniqueId();
		if (Config.getConfig().isSet(path)){
			return Config.getInteger(path);
		} else {
			setVotingPoints(player, 0);
			return 0;
		}
	}
	
	public static void setVotingPoints(Player player, int i){
		String path = "voting." + player.getUniqueId();
		Config.set(path, i);
	}
	
	public static void addVotingPoints(Player player, int i){
		setVotingPoints(player, getVotingPoints(player) + i);
	}
	
	public static void removeVotingPoints(Player player, int i){
		setVotingPoints(player, getVotingPoints(player) - i);
	}
	
	public static boolean hasVotingPoints(Player player, int i){
		return getVotingPoints(player) >= i;
	}
	
	public static void openVotingBox(Player player){
		setPlayerOpeningBox(true);
		oldPlayerLocation = player.getLocation();
		player.teleport(new Location(Var.world, 3.5, 71, -62.5, 0, 0));		
	}
	
	public static int getRandomXP(){
		return NumberUtils.randomInteger(20, 100);
	}
	
	public static int getRandomGold(){
		return NumberUtils.randomInteger(0, 50);
	}
	
	public static int getRandomLife(){
		return NumberUtils.randomInteger(0, 2);
	}
	
	public static boolean isVotingChest(Block block){
		Location loc = block.getLocation();
		return 	loc.getBlockX() == 3 &&
				loc.getBlockY() == 71 &&
				loc.getBlockZ() == -55;
	}
	
	public static Location getOldPlayerLocation(){
		return oldPlayerLocation;
	}

	public static boolean isPlayerOpeningBox() {
		return playerOpeningBox;
	}

	public static void setPlayerOpeningBox(boolean playerOpeningBox) {
		Voting.playerOpeningBox = playerOpeningBox;
	}

}
