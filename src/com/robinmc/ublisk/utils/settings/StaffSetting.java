package com.robinmc.ublisk.utils.settings;

import org.bukkit.entity.Player;

import com.robinmc.ublisk.utils.Config;
import com.robinmc.ublisk.utils.exception.NotSetException;

public enum StaffSetting {
	
	COMMAND_LOG("Enable command log", "command-log", "See commands players execute."),
	INFO_MESSAGES("Enable info message", "info-msg", "See info messages (green messages)");
	
	private String s;
	private String name;
	private String info;
	
	StaffSetting(String name, String s, String info){
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
		String path = "staffsettings." + s + "." + player.getUniqueId();
		if (Config.getConfig().isSet(path)){
			return Config.getBoolean(path);
		} else {
			throw new NotSetException();
		}
	}
	
	public void put(Player player, boolean bool){
		Config.set("staffsettings." + s + "." + player.getUniqueId(), bool);
	}
	
	public static StaffSetting fromName(String name){
		for (StaffSetting setting : StaffSetting.values()){
			if (name.equalsIgnoreCase(setting.getName())){
				return setting;
			}
		}
		throw new IllegalArgumentException();
	}

}
