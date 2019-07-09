package xyz.derkades.ublisk.commands;

@Deprecated
public class SuggestCommand {/*implements CommandExecutor {

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
	}*/

}
