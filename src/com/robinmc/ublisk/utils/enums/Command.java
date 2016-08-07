package com.robinmc.ublisk.utils.enums;

import org.bukkit.command.CommandExecutor;

import com.robinmc.ublisk.commands.Afk;
import com.robinmc.ublisk.commands.ClassCommand;
import com.robinmc.ublisk.commands.Credits;
import com.robinmc.ublisk.commands.Debug;
import com.robinmc.ublisk.commands.FriendsCommand;
import com.robinmc.ublisk.commands.Help;
import com.robinmc.ublisk.commands.Menu;
import com.robinmc.ublisk.commands.MuteCommand;
import com.robinmc.ublisk.commands.Report;
import com.robinmc.ublisk.commands.Suggest;

public enum Command {
	
	AFK("afk", new Afk()),
	CLASS("class", new ClassCommand()),
	CREDITS("credits", new Credits()),
	DEBUG("debug", new Debug()),
	FRIENDS("friends", new FriendsCommand()),
	HELP("help", new Help()),
	MENU("menu", new Menu()),
	MUTE("mute", new MuteCommand()),
	REPORT("report", new Report()),
	SUGGEST("suggest", new Suggest());
	
	private String cmd;
	private CommandExecutor exec;
	
	Command(String cmd, CommandExecutor exec){
		this.cmd = cmd;
		this.exec = exec;
	}
	
	public String getCommand(){
		return cmd;
	}
	
	public CommandExecutor getExecutor(){
		return exec;
	}
	
}
