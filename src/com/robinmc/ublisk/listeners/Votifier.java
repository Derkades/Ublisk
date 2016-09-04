package com.robinmc.ublisk.listeners;

import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.robinmc.ublisk.utils.Voting;
import com.robinmc.ublisk.utils.logging.LogLevel;
import com.robinmc.ublisk.utils.logging.Logger;
import com.robinmc.ublisk.utils.variable.CMessage;
import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;

public class Votifier implements Listener {
	
	@EventHandler
	public void onVote(VotifierEvent event){
		Vote vote = event.getVote();
		Player player = Bukkit.getPlayer(vote.getUsername());
		int points = ThreadLocalRandom.current().nextInt(1, 3 + 1);
		Voting.addVotingPoints(player, points);
		Bukkit.broadcastMessage(CMessage.vote(player.getName(), points));
		Logger.log(LogLevel.INFO, "Vote", player.getName() + " has voted at " + vote.getServiceName() + " (" + vote.getAddress() + ") and got " + points + " points.");
	}

}
