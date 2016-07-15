package com.robinmc.ublisk.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.robinmc.ublisk.Message;

public class Report implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		sender.sendMessage(Message.REPORT_FORUMS.get());
		return true;
	}

}
