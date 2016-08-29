package com.robinmc.ublisk.utils.perm;

import org.bukkit.entity.Player;

import com.robinmc.ublisk.utils.Config;
import com.robinmc.ublisk.utils.exception.GroupNotFoundException;
import com.robinmc.ublisk.utils.logging.LogLevel;
import com.robinmc.ublisk.utils.logging.Logger;

public class PermissionPlayer {
	
	private Player player;
	
	public PermissionPlayer(Player player){
		this.player = player;
	}
	
	public Player getPlayer(){
		return player;
	}
	
	public PermissionGroup getGroup(){
		try {
			return PermissionGroup.fromString(Config.getString("groups." + player.getUniqueId()));
		} catch (GroupNotFoundException e) {
			Logger.log(LogLevel.WARNING, "Permissions", "Could not get group of " + player.getName());
			return PermissionGroup.DEFAULT;
		}
	}
	
	public boolean hasPermission(Permission perm){
		return getGroup().hasPermission(perm);
	}

}
