package com.robinmc.ublisk.enums;

import org.bukkit.entity.Player;

import com.robinmc.ublisk.utils.Config;
import com.robinmc.ublisk.utils.exception.NotSetException;

public enum Setting {
	
	FRIENDS_SHOW_HEALTH("Show friend's health", "friends-show-health", "Shows friend's health in a boss bar."),
	PLAY_MUSIC("Play music", "music", "Music is different for every town."),
	PM_SOUND("Play PM sound", "pm-sound", "Play a sound if someone sends you a private message.");
	
	private String s;
	private String name;
	private String info;
	
	Setting(String name, String s, String info){
		this.s = s;
		this.name = name;
		this.info = info;
	}
	
	public String getString(){
		return s;
	}
	
	public String getName(){
		return name;
	}
	
	public String getInfo(){
		return info;
	}
	
	public boolean get(Player player) throws NotSetException{
		String path = "settings." + s + "." + player.getUniqueId();
		if (Config.getConfig().isSet(path)){
			return Config.getBoolean(path);
		} else {
			throw new NotSetException();
		}
	}
	
	public void put(Player player, boolean bool){
		Config.set("settings." + s + "." + player.getUniqueId(), bool);
	}

}
