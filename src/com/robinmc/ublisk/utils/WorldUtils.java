package com.robinmc.ublisk.utils;

import org.bukkit.Bukkit;
import org.bukkit.World;

public class WorldUtils {
	
	public static void saveWorlds(){
		for (World world : Bukkit.getWorlds()){
			world.save();
		}
	}

}
