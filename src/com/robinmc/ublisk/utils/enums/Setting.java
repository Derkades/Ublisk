package com.robinmc.ublisk.utils.enums;

import org.bukkit.entity.Player;

import com.robinmc.ublisk.utils.Config;
import com.robinmc.ublisk.utils.exception.NotSetException;

public enum Setting {
	
	FRIENDS_SHOW_HEALTH("friends-show-health"),
	PLAY_MUSIC("music"),
	PM_SOUND("pm-sound");
	
	private String s;
	
	Setting(String s){
		this.s = s;
	}
	
	public String getString(){
		return s;
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
