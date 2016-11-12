package com.robinmc.ublisk.utils;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;

import com.robinmc.ublisk.Main;

@Deprecated
public class Config {
	
	static FileConfiguration config = Main.getInstance().getConfig();
	
	public static void create(){
		try {
			Main main = Main.getInstance();
			File file = new File(main.getDataFolder(), "config.yml"); //Set file variable
			if (!main.getDataFolder().exists()) //Check if folder doesn't exist
				main.getDataFolder().mkdirs(); //Then make one
			if (!file.exists()){ //If file doesn't exist
				main.getLogger().info("config.yml not found, creating...");
				main.saveConfig(); //Make one
			} else { //Otherwise just send message
				main.getLogger().info("config.yml found, loading...");
			}
		} catch (Exception e){ //If everything fails
			System.out.println("Critical error while creating a config!");
			e.printStackTrace();
		}
	}
	
	public static void reload(){
		Main main = Main.getInstance();
		main.reloadConfig(); //Reload the config. Pretty simple :D
	}
	
	public static void save(){
		Main.getInstance().saveConfig();
	}
	
	/**
	 * Adds a string to the config file
	 * @param path Path in config file
	 * @param string String to add to config file
	 */
	public static void set(String path, String string){
		config.set(path, string);
		save();
	}
	
	public static void set(String path, boolean state){
		config.set(path, state);
		save();
	}
	
	public static void set(String path, int i){
		config.set(path, i);
		save();
	}
	
	public static void set(String path, double n){
		config.set(path, n);
		save();
	}
	
	public static String getString(String path){
		return config.getString(path);
	}
	
	public static boolean getBoolean(String path){
		return config.getBoolean(path);
	}
	
	public static int getInteger(String path){
		return config.getInt(path);
	}
	
	public static double getDouble(String path){
		return config.getDouble(path);
	}
	
	public static FileConfiguration getConfig(){
		return config;
	}

}
