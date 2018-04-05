package xyz.derkades.ublisk.commands;

import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.SimplePluginManager;

import xyz.derkades.ublisk.Main;
import xyz.derkades.ublisk.commands.ublisk.UbliskCommand;
import xyz.derkades.ublisk.modules.AFK;
import xyz.derkades.ublisk.utils.Logger;
import xyz.derkades.ublisk.utils.Logger.LogLevel;

public enum Command {

	AFK("afk", new AFK()),
	BUILDER("builder", new Builder()),
	//CLASS("class", new ClassCommand()),
	CONTACT("contact", new Contact()),
	CREDITS("credits", new Credits()),
	DEBUG("debug", new Debug()),
	FRIENDS("friends", new FriendsCommand()),
	GUILDS("guild", new GuildCommand()),
	HELP("help", new Help()),
	MENU("menu", new Menu()),
	MSG("msg", new MsgCommand()),
	MUTE("mute", new MuteCommand()),
	REPLY("reply", new ReplyCommand()),
	STATS("stats", new StatsCommand()),
	SUGGEST("suggest", new SuggestCommand()),
	
	UBLISK("ublisk", new UbliskCommand.Executor());

	private String cmd;
	private CommandExecutor exec;

	Command(String cmd, CommandExecutor exec) {
		this.cmd = cmd;
		this.exec = exec;
	}

	private String getCommand() {
		return cmd;
	}

	private CommandExecutor getExecutor() {
		return exec;
	}
	
	private static void unregisterExistingCommands() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		String[] commands = {"u"};
		
		SimplePluginManager spm = (SimplePluginManager) Bukkit.getServer().getPluginManager();
		Field f = SimplePluginManager.class.getDeclaredField("commandMap");
		f.setAccessible(true);
		SimpleCommandMap scm = (SimpleCommandMap) f.get(spm);
		f.setAccessible(false);
		
		for (String command : commands) {
			Bukkit.getPluginCommand(command).unregister(scm);
		}
	}

	public static void registerAll() {
		try {
			unregisterExistingCommands();
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
        
		Logger.log(LogLevel.INFO, "Commands", "Registering commands...");
		for (Command cmd : Command.values()) {
			Logger.log(LogLevel.DEBUG, "Commands",
					"Registered command with class " + cmd.getExecutor().getClass().getSimpleName());
			String command = cmd.getCommand();
			CommandExecutor executor = cmd.getExecutor();
			try {
				Main.getInstance().getCommand(command).setExecutor(executor);
			} catch (NullPointerException e) {
				Logger.log(LogLevel.SEVERE, "Commands",
						"The command /" + cmd.getCommand() + " is not specified in the plugin.yml file");
			}
		}
	}

}
