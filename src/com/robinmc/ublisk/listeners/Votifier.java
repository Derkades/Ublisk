package com.robinmc.ublisk.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.robinmc.ublisk.Message;
import com.robinmc.ublisk.utils.Logger;
import com.robinmc.ublisk.utils.Logger.LogLevel;
import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.exception.PlayerNotFoundException;
import com.robinmc.ublisk.utils.java.Random;
import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;

public class Votifier implements Listener {
	
	@EventHandler
	public void onVote(VotifierEvent event){
		Vote vote = event.getVote();
		UPlayer player;
		try {
			player = UPlayer.get(vote.getUsername());
		} catch (PlayerNotFoundException e) {
			e.printStackTrace(); //TODO Deal with player not online
			return;
		}
		
		int points = Random.getRandomInteger(1, 3);
		player.setVotingPoints(player.getVotingPoints() + points);
		Bukkit.broadcastMessage(Message.Complicated.vote(player.getName(), points));
		Logger.log(LogLevel.INFO, "Vote", player.getName() + " has voted at " + vote.getServiceName() + " (" + vote.getAddress() + ") and got " + points + " points.");
	}

}
