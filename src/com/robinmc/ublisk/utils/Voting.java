package com.robinmc.ublisk.utils;

import org.bukkit.entity.Player;

public class Voting {
	
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

}
