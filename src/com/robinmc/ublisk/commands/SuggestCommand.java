package com.robinmc.ublisk.commands;

import java.io.File;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.Message;
import com.robinmc.ublisk.utils.java.FileUtils;

public class SuggestCommand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		//sender.sendMessage(Message.REPORT.toString());
		//return true;
		
		if (args.length < 1) {
			sender.sendMessage(Message.WRONG_USAGE.toString());
		}
		
		File file = new File(Main.getInstance().getDataFolder(), "suggestions.txt");
		FileUtils.appendStringToFile(file, String.join(" ", args) + "\n\n");
		
		sender.sendMessage("Your message has been recorded. We'll take a look at it soon, hang tight!");
		
		return true;
	}

}
