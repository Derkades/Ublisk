package com.robinmc.ublisk.utils;

import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.commands.Afk;
import com.robinmc.ublisk.commands.Credits;
import com.robinmc.ublisk.commands.Debug;
import com.robinmc.ublisk.commands.Help;
import com.robinmc.ublisk.commands.Menu;
import com.robinmc.ublisk.commands.Report;
import com.robinmc.ublisk.commands.Suggest;

public class Commands {
	
	public static void register(){
		Console.sendMessage("[Ublisk] Registering commands...");
		Main plugin = Main.getInstance();
		plugin.getCommand("afk").setExecutor(new Afk());
		plugin.getCommand("credits").setExecutor(new Credits());
		plugin.getCommand("debug").setExecutor(new Debug());
		plugin.getCommand("help").setExecutor(new Help());
		plugin.getCommand("menu").setExecutor(new Menu());
		plugin.getCommand("music").setExecutor(new com.robinmc.ublisk.commands.Music());
		plugin.getCommand("report").setExecutor(new Report());
		plugin.getCommand("suggest").setExecutor(new Suggest());
	}

}
