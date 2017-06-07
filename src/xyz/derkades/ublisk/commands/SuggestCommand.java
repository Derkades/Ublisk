package xyz.derkades.ublisk.commands;

import java.sql.SQLException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.md_5.bungee.api.ChatColor;
import xyz.derkades.ublisk.Message;
import xyz.derkades.ublisk.utils.Logger;
import xyz.derkades.ublisk.utils.TodoList;
import xyz.derkades.ublisk.utils.Ublisk;
import xyz.derkades.ublisk.utils.Logger.LogLevel;
import xyz.derkades.ublisk.utils.TodoList.TodoItem;

public class SuggestCommand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (args.length < 1) {
			sender.sendMessage(Message.WRONG_USAGE.toString());
			return true;
		}

		TodoItem todoItem = new TodoItem(0, "Ublisk", String.join(" ", args));
		
		try {
			TodoList.addTodoItem(todoItem);
			sender.sendMessage(Ublisk.getPrefix() + "Your message has been recorded. We'll take a look at it soon!");
		} catch (SQLException e){
			Logger.log(LogLevel.SEVERE, "Database error (todo list suggestion)");
			sender.sendMessage(ChatColor.RED + "An error occured :(");
			e.printStackTrace();
		}
		
		return true;
	}

}
