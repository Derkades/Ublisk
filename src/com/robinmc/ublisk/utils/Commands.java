package com.robinmc.ublisk.utils;

import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.commands.Credits;
import com.robinmc.ublisk.commands.Debug;
import com.robinmc.ublisk.commands.Help;
import com.robinmc.ublisk.commands.Menu;

public class Commands {
	
	public static void register(){
		Console.sendMessage("[Ublisk] Registering commands...");
		Main plugin = Main.getInstance();
		plugin.getCommand("credits").setExecutor(new Credits());
		plugin.getCommand("debug").setExecutor(new Debug());
		plugin.getCommand("help").setExecutor(new Help());
		plugin.getCommand("menu").setExecutor(new Menu());
		plugin.getCommand("music").setExecutor(new com.robinmc.ublisk.commands.Music());
	}

}
