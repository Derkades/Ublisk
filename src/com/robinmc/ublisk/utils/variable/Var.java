package com.robinmc.ublisk.utils.variable;

import org.bukkit.Bukkit;
import org.bukkit.World;

public class Var {
	
	public static World world(){
		return Bukkit.getWorld("ublisk");
	}
	
	public static String pack(){
		return "http://ublisk.robinmc.com/textures.zip";
	}
	
	public static int doubleExpTime(){
		return 60;
	}

}