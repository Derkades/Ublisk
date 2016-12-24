package com.robinmc.ublisk.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.robinmc.ublisk.Message;
import com.robinmc.ublisk.utils.UPlayer;

public class VoteRestartCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)){
			sender.sendMessage(Message.NOT_A_PLAYER.toString());
		}
		
		UPlayer player = UPlayer.get(sender);
		
		if (player.hasVotedForRestart()){
			player.sendMessage(Message.ALREADY_VOTED_RESTART);
			return true;
		}
		
		player.voteRestart();
		return true;
	}

}
