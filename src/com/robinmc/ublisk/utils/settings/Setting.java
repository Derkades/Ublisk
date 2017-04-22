package com.robinmc.ublisk.utils.settings;

import org.bukkit.entity.Player;

import com.robinmc.ublisk.DataFile;

public enum Setting {
	
	FRIENDS_SHOW_HEALTH("Show friend's health", "friends-show-health", "Shows friend's health in a sidebar.", true),
	PLAY_MUSIC("Play music", "music", "Music is different for every town.", true),
	PM_SOUND("Play PM sound", "pm-sound", "Play a sound if someone sends you a private message.", true);
	
	private String config;
	private String name;
	private String info;
	private boolean defaultValue;
	
	Setting(String name, String config, String info, boolean defaultValue){
		this.config = config;
		this.name = name;
		this.info = info;
		this.defaultValue = defaultValue;
	}
	
	public String getString(){
		return config;
	}
	
	public String getName(){
		return name;
	}
	
	public String getInfo(){
		return info;
	}
	
	public boolean getDefaultValue(){
		return defaultValue;
	}
	
	public boolean get(Player player){
		String path = "settings." + config + "." + player.getUniqueId();
		if (DataFile.SETTINGS.getConfig().isSet(path)){
			return DataFile.SETTINGS.getConfig().getBoolean(path);
		} else {
			return defaultValue;
		}
	}
	
	public void put(Player player, boolean bool){
		DataFile.SETTINGS.getConfig().set("settings." + config + "." + player.getUniqueId(), bool);
	}
	
	public static Setting fromName(String name){
		for (Setting setting : Setting.values()){
			if (name.equalsIgnoreCase(setting.getName())){
				return setting;
			}
		}
		throw new IllegalArgumentException();
	}

}
