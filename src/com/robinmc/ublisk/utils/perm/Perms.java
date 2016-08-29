package com.robinmc.ublisk.utils.perm;

import org.bukkit.entity.Player;

public class Perms {
	
	public static PermissionPlayer getPermissionPlayer(Player player){
		return new PermissionPlayer(player);
	}

}
