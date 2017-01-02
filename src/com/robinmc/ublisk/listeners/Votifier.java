package com.robinmc.ublisk.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.robinmc.ublisk.Var;
import com.robinmc.ublisk.utils.Logger;
import com.robinmc.ublisk.utils.Logger.LogLevel;
import com.robinmc.ublisk.utils.UPlayer;
import com.robinmc.ublisk.utils.Ublisk;
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
			player = new UPlayer(vote.getUsername());
		} catch (PlayerNotFoundException e) {
			e.printStackTrace(); //TODO Deal with player not online exception
			return;
		}
		
		int points = Random.getRandomInteger(1, 3);
		player.setVotingPoints(player.getVotingPoints() + points);
		Ublisk.broadcastPrefixedMessage(player.getName() + " has voted and got " + points + " points! Vote at " + Var.VOTE_URL);
		Logger.log(LogLevel.INFO, "Vote", player.getName() + " has voted at " + vote.getServiceName() + " (" + vote.getAddress() + ") and got " + points + " points.");
	}

}
