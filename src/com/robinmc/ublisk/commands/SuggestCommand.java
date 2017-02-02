package com.robinmc.ublisk.commands;

import java.sql.SQLException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.robinmc.ublisk.Message;
import com.robinmc.ublisk.utils.Logger;
import com.robinmc.ublisk.utils.Logger.LogLevel;
import com.robinmc.ublisk.utils.TodoList;
import com.robinmc.ublisk.utils.TodoList.TodoItem;

import net.md_5.bungee.api.ChatColor;

public class SuggestCommand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		//sender.sendMessage(Message.REPORT.toString());
		//return true;
		
		if (args.length < 1) {
			sender.sendMessage(Message.WRONG_USAGE.toString());
		}
		
		//File file = new File(Main.getInstance().getDataFolder(), "suggestions.txt");
		//FileUtils.appendStringToFile(file, String.join(" ", args) + "\n\n");
		
		TodoItem todoItem = new TodoItem(0, "Ublisk", String.join(" ", args));
		
		try {
			TodoList.addTodoItem(todoItem);
			sender.sendMessage("Your message has been recorded. We'll take a look at it soon!");
		} catch (SQLException e){
			Logger.log(LogLevel.SEVERE, "Database error (todo list suggestion)");
			sender.sendMessage(ChatColor.RED + "An error occured :(");
			e.printStackTrace();
		}
		
		return true;
	}

}
