package com.robinmc.ublisk.utils.enums;

import org.bukkit.entity.Player;

import com.robinmc.ublisk.utils.Config;

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
	
	public boolean put(Player player){
		try {
			return Config.getBoolean("settings." + s + "." + player.getUniqueId());
		} catch (Exception e){
			return false;
		}
	}
	
	public void set(Player player, boolean bool){
		Config.set("settings." + s + "." + player.getUniqueId(), bool);
	}

}
