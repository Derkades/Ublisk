package com.robinmc.ublisk.commands;

import org.bukkit.command.CommandExecutor;

import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.utils.logging.LogLevel;
import com.robinmc.ublisk.utils.logging.Logger;

public enum Command {
	
	AFK("afk", new Afk()),
	BUILDER("builder", new Builder()),
	CLASS("class", new ClassCommand()),
	CONTACT("contact", new Contact()),
	CREDITS("credits", new Credits()),
	DEBUG("debug", new Debug()),
	FRIENDS("friends", new FriendsCommand()),
	GUILDS("guild", new GuildCommand()),
	HELP("help", new Help()),
	MENU("menu", new Menu()),
	MSG("msg", new MsgCommand()),
	MUTE("mute", new MuteCommand()),
	REPORT("report", new Report()),
	RESOURCE_PACK("pack", new ResourcePackCommand()),
	VOTE_RESTART("voterestart", new VoteRestartCommand());
	
	private String cmd;
	private CommandExecutor exec;
	
	Command(String cmd, CommandExecutor exec){
		this.cmd = cmd;
		this.exec = exec;
	}
	
	private String getCommand(){
		return cmd;
	}
	
	private CommandExecutor getExecutor(){
		return exec;
	}
	
	public static void registerAll(){
		Logger.log(LogLevel.INFO, "Commands", "Registering commands...");
		for (Command cmd : Command.values()){
			Logger.log(LogLevel.INFO, "Commands", "Registered command with class " + cmd.getExecutor().getClass().getSimpleName());
			String command = cmd.getCommand();
			CommandExecutor executor = cmd.getExecutor();
			try {
				Main.getInstance().getCommand(command).setExecutor(executor);
			} catch (NullPointerException e){
				Logger.log(LogLevel.SEVERE, "Commands", "The command /" + cmd.getCommand() + " is not specified in the plugin.yml file");
			}
		}
	}
	
}
