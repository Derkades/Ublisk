package com.robinmc.ublisk.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.robinmc.ublisk.utils.Exp;

import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import static org.bukkit.ChatColor.*;

public class AsyncPlayerChat implements Listener {
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent event){
		Player player = event.getPlayer();
		PermissionUser pu = PermissionsEx.getUser(player);
		@SuppressWarnings("deprecation")
		String prefix = pu.getGroups()[0].getPrefix().replace("&", "#");
		int level = Exp.get(player);
		String format = DARK_GRAY + "[" + GRAY + level + DARK_GRAY + "] " + prefix + player.getName() + DARK_GRAY + ": ";
		event.setFormat(format);
	}

}
