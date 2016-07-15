package com.robinmc.ublisk.utils;

import org.bukkit.entity.Player;

public enum Setting {
	
	FRIENDS_SHOW_HEALTH("friends-show-health"),
	PLAY_MUSIC("music");
	
	private String s;
	
	Setting(String s){
		this.s = s;
	}
	
	public String getString(){
		return s;
	}
	
	public boolean get(Player player){
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
