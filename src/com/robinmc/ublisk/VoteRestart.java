package com.robinmc.ublisk;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import com.robinmc.ublisk.utils.UPlayer;

public class VoteRestart {

	private static List<String> restartVoters = new ArrayList<String>();
	private static boolean restarting = false;

	private static void restartIfPossible(){
		int online = Bukkit.getOnlinePlayers().size();
		int voters = restartVoters.size();
		if (voters > (online / 2) && !restarting){ //If the number of voters is more than half of the player count, and if not already restarting
			restarting = true;
			new BukkitRunnable(){
				public void run(){
					Bukkit.broadcastMessage(Message.Complicated.serverRestartingWarningMinutes(1));
				}
			}.runTaskLater(Main.getInstance(), 5*20);
			new BukkitRunnable(){
				public void run(){
					Bukkit.broadcastMessage(Message.Complicated.serverRestartingWarningSeconds(30));
				}
			}.runTaskLater(Main.getInstance(), 5*20 + 30*20);
			new BukkitRunnable(){
				public void run(){
					Bukkit.broadcastMessage(Message.Complicated.serverRestartingWarningSeconds(10));
				}
			}.runTaskLater(Main.getInstance(), 5*20 + 30*20 + 20*20);
			new BukkitRunnable(){
				public void run(){
					Bukkit.broadcastMessage(Message.Complicated.serverRestartingWarningSeconds(5));
				}
			}.runTaskLater(Main.getInstance(), 5*20 + 30*20 + 20*20 + 5*20);
			new BukkitRunnable(){
				public void run(){
					Bukkit.getServer().shutdown();
				}
			}.runTaskLater(Main.getInstance(), 5*20 + 30*20 + 20*20 + 5*20 + 5*20);
		}
	}
	
	public static void voteForRestart(UPlayer player){
		restartVoters.add(player.getName());
		Bukkit.broadcastMessage(Message.Complicated.someoneVotedRestart(player.getName(), restartVoters.size()));
		restartIfPossible();
	}
	
	public static boolean hasVotedForRestart(UPlayer player){
		return restartVoters.contains(player.getName());
	}

}
